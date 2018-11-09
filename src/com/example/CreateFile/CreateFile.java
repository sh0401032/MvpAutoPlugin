package com.example.CreateFile;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

public class CreateFile extends WriteCommandAction.Simple {
    private final PsiElementFactory factory;
    private final Project project;
    private final String className;
    private final JavaDirectoryService directoryService;
    private final PsiDirectory directory;
    private int type = -1;

    public CreateFile(AnActionEvent e, String className, int type) {
        super(e.getProject());
        this.project = e.getProject();
        this.className = className;
        factory = JavaPsiFacade.getElementFactory(project);
        directoryService = JavaDirectoryService.getInstance();
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
        directory = ideView.getOrChooseDirectory();
        this.type = type;
    }

    @Override
    protected void run() throws Throwable {
        createFiles();
    }

    private void createFiles() {
        createContract();
        createPresenter();
        createModel();
        if (type == 0) {
            createActivity();
        } else if (type == 1) {
            createFragment();
        }
    }

    private void createContract() {
        directoryService.createClass(directory, className, "MVPContract");
    }

    private void createActivity() {
        directoryService.createClass(directory, className, "MVPActivity");
    }

    private void createModel() {
        directoryService.createClass(directory, className, "MVPModel");
    }

    private void createPresenter() {
        directoryService.createClass(directory, className, "MVPPresenter");
    }

    private void createFragment() {
        directoryService.createClass(directory, className, "MVPFragment");
    }
}
