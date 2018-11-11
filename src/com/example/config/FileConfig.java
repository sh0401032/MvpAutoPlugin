package com.example.config;


import com.example.UI.ConfigFileDialog;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.annotation.Nullable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;


import static com.example.config.Config.*;

public class FileConfig implements SearchableConfigurable {

    public PropertiesComponent state;
    public ConfigFileDialog mCp;
    public Project mProject;
    public TreeClassChooserFactory classChooserFactory;

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "MvpAutoPlugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        if (projects != null && projects.length >= 1) {
            mProject = projects[0];
        } else {
            mProject = ProjectManager.getInstance().getDefaultProject();
        }
        classChooserFactory = TreeClassChooserFactory.getInstance(mProject);
        state = PropertiesComponent.getInstance(mProject);
        mCp = new ConfigFileDialog();
        //loadValues();
        return mCp.contentPanel;
    }

    @Override
    public boolean isModified() {
//        return mCp.tvView.getText() != state.getValue(SUPER_VIEW) ||
//                mCp.tvPresenter.getText() != state.getValue(SUPER_PRESENTER) ||
//                mCp.tvFragment.getText() != state.getValue(SUPER_VIEW_FRAGMENT) ||
//                mCp.tvActivity.getText() != state.getValue(SUPER_VIEW_ACTIVITY);
        return true;
    }

    @Override
    public void apply() {
        state.setValue(SUPER_VIEW, mCp.tvView.getText());
        state.setValue(SUPER_PRESENTER, mCp.tvPresenter.getText());
        state.setValue(SUPER_VIEW_ACTIVITY, mCp.tvActivity.getText());
        state.setValue(SUPER_VIEW_FRAGMENT, mCp.tvFragment.getText());
    }

    private void loadValues() {
        mCp.tvView.setText(state.getValue(SUPER_VIEW));
        setClassChooser(mCp.btView, "Select Super View Interface", mCp.tvView);
        mCp.tvPresenter.setText(state.getValue(SUPER_PRESENTER));
        setClassChooser(mCp.btPresenter, "Select Super Presenter", mCp.tvPresenter);
        mCp.tvActivity.setText(state.getValue(SUPER_VIEW_ACTIVITY));
        setClassChooser(mCp.btActivity, "Select Super Activity", mCp.tvActivity);
        mCp.tvFragment.setText(state.getValue(SUPER_VIEW_FRAGMENT));
        setClassChooser(mCp.btFragment, "Select Super Fragment", mCp.tvFragment);
    }

    private void setClassChooser(JButton jButton, String title, JTextField jTextField) {
        if (jButton.getActionListeners() != null) return;
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeClassChooser treeClassChooser = classChooserFactory.createProjectScopeChooser(title);
                treeClassChooser.showDialog();
                if (treeClassChooser.getSelected() != null) {
                    jTextField.setText(treeClassChooser.getSelected().getQualifiedName());
                }
            }
        });
    }
}
