package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class MyAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile baseDir = e.getProject().getBaseDir();
        final PsiDirectory projectDir = PsiManager.getInstance(e.getProject()).findDirectory(baseDir);
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
        PsiFile fileFromText = PsiFileFactory.getInstance(e.getProject()).createFileFromText("myfile",FileTypes.PLAIN_TEXT, output);
        PsiFile myfile = Objects.requireNonNull(projectDir).findFile("myfile");
        Application application = ApplicationManager.getApplication();
        application.runWriteAction(() -> {
        if (myfile != null) {
         myfile.delete();
        }
        Objects.requireNonNull(projectDir).add(fileFromText);
        });
    }
}
