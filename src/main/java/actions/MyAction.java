package actions;

import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class MyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        NotificationGroup myplugin = new NotificationGroup("myplugin", NotificationDisplayType.BALLOON, true);
        PsiFile[] files = FilenameIndex.getFilesByName(e.getProject(),"MainActivity.java",GlobalSearchScope.projectScope(e.getProject()));
        String output="";
        for (int i = 0; i < files.length; i++) {
            PsiJavaFile javaFile = (PsiJavaFile)files[i];
            for (int j = 0; j < javaFile.getClasses()[0].getMethods().length; j++) {
                output=output+"file["+javaFile.getClasses()[0].getMethods()[j].getName()+"] \n";
            }
        }
        myplugin.createNotification("My Title",
                output,
                NotificationType.INFORMATION,
                null).notify(e.getProject());
    }
}
