package com.example.UI;


import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ConfigFilePanel implements Configurable {
    public JPanel contentPanel;
    public JTextField tvView;
    public JButton btView;
    public JTextField tvPresenter;
    public JButton btPresenter;
    public JTextField tvActivity;
    public JButton btActivity;
    public JTextField tvFragment;
    public JButton btFragment;

    public PropertiesComponent state;
    public Project mProject;
    public TreeClassChooserFactory classChooserFactory;

    public ConfigFilePanel(Project project) {

    }

    public void createUIComponents() {

    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "MvpAutoPlugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return contentPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {

    }

    @Override
    public void disposeUIResources() {
        contentPanel = null;
    }

    private void init() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        if (projects != null && projects.length >= 1) {
            mProject = projects[0];
        } else {
            mProject = ProjectManager.getInstance().getDefaultProject();
        }
        classChooserFactory = TreeClassChooserFactory.getInstance(mProject);
        state = PropertiesComponent.getInstance(mProject);
    }
}
