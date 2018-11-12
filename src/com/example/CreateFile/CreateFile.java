package com.example.CreateFile;

import com.example.config.Config;
import com.intellij.ide.IdeView;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElementFactory;

import java.util.HashMap;

public class CreateFile extends WriteCommandAction.Simple {
    private final PsiElementFactory factory;
    private final Project project;
    private final String className;
    private final JavaDirectoryService directoryService;
    private final PsiDirectory directory;
    PropertiesComponent mState;
    HashMap<String, String> templateProperties;

    public CreateFile(AnActionEvent e, String className) {
        super(e.getProject());
        this.project = e.getProject();
        this.className = className;
        factory = JavaPsiFacade.getElementFactory(project);
        directoryService = JavaDirectoryService.getInstance();
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
        directory = ideView.getOrChooseDirectory();
        mState = PropertiesComponent.getInstance(this.project);
        String superView = mState.getValue(Config.SUPER_VIEW);
        String superPresenter = mState.getValue(Config.SUPER_PRESENTER);
        String superActivity = mState.getValue(Config.SUPER_VIEW_ACTIVITY);
        String superFragment = mState.getValue(Config.SUPER_VIEW_FRAGMENT);
        templateProperties = new HashMap<String, String>();
        templateProperties.put("VIEW", superView);
        templateProperties.put("VIEW_NAME", superView.substring(superView.lastIndexOf(".") + 1));
        templateProperties.put("PRESENTER", superPresenter);
        templateProperties.put("PRESENTER_NAME", superPresenter.substring(superPresenter.lastIndexOf(".") + 1));
        templateProperties.put("ACTIVITY", superActivity);
        templateProperties.put("ACTIVITY_NAME", superActivity.substring(superActivity.lastIndexOf(".") + 1));
        templateProperties.put("FRAGMENT", superFragment);
        templateProperties.put("FRAGMENT_NAME", superFragment.substring(superFragment.lastIndexOf(".") + 1));
    }

    @Override
    protected void run() throws Throwable {
        createFiles();
    }

    private void createFiles() {
        createContract();
        createPresenter();
        createModel();
        if (Config.TYPE_ACTIVITY.equals(mState.getValue(Config.TYPE))) {
            createActivity();
        } else if (Config.TYPE_FRAGMENT.equals(mState.getValue(Config.TYPE))) {
            createFragment();
        }
    }

    private void createContract() {
        directoryService.createClass(directory, className, "MVPContract", false, templateProperties);
    }

    private void createActivity() {
        directoryService.createClass(directory, className, "MVPActivity", false, templateProperties);
    }

    private void createModel() {
        directoryService.createClass(directory, className, "MVPModel", false, templateProperties);
    }

    private void createPresenter() {
        directoryService.createClass(directory, className, "MVPPresenter", false, templateProperties);
    }

    private void createFragment() {
        directoryService.createClass(directory, className, "MVPFragment", false, templateProperties);
    }
}
