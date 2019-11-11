package actions;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class MyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        NotificationGroup myplugin = new NotificationGroup("myplugin", NotificationDisplayType.BALLOON, true);
        Collection<VirtualFile> java = FilenameIndex.getAllFilesByExt(Objects.requireNonNull(e.getProject()), "java", GlobalSearchScope.projectScope(e.getProject()));
        Iterator<VirtualFile> iterator = java.iterator();
        StringBuilder output= new StringBuilder();
        while (iterator.hasNext()){
            PsiJavaFile javaFile = (PsiJavaFile) PsiManager.getInstance(e.getProject()).findFile(iterator.next());
            for (int i = 0; i < Objects.requireNonNull(javaFile).getClasses().length; i++) {
                for (int j = 0; j < javaFile.getClasses()[i].getMethods().length; j++) {
                    output.append(javaFile.getPackageName()).append(".").append(javaFile.getClasses()[i].getName()).append(".").append(javaFile.getClasses()[i].getMethods()[j].getName()).append(" \n");
                }
            }
        }
        myplugin.createNotification("My Title",
                output.toString(),
                NotificationType.INFORMATION,
                null).notify(e.getProject());
    }
}
