package com.example.UI;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.example.CreateFile.CreateFile;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.example.config.Config.*;

public class CreateFileDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;
    private JRadioButton activityRadioButton;
    private JRadioButton fragmentRadioButton;
    private JTextField tv_iview;
    private JTextField tv_presenter;
    private JTextField tv_activity;
    private JButton bt_iview;
    private JButton bt_presenter;
    private JButton bt_activity;
    private JTextField tv_fragment;
    private JButton bt_fragment;
    private int type = -1;
    private final static int TYPE_ACTIVITY = 0;
    private final static int TYPE_FRAGMENT = 1;

    public PropertiesComponent state;
    public Project mProject;
    public TreeClassChooserFactory classChooserFactory;

    public CreateFileDialog(AnActionEvent e) {
        initProject();
        setTitle("New Mvp File");
        setContentPane(contentPane);
        setModal(true);
        setMinimumSize(new Dimension(260, 180));
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);
        activityRadioButton.addActionListener(e1 -> onSelectModel(e, TYPE_ACTIVITY));
        fragmentRadioButton.addActionListener(e1 -> onSelectModel(e, TYPE_FRAGMENT));
        activityRadioButton.setSelected(true);
        type = TYPE_ACTIVITY;
        buttonOK.addActionListener(e1 -> onOK(e));

        buttonCancel.addActionListener(e1 -> onCancel());
        bt_iview.addActionListener(e1 -> onChooserClass(tv_iview, "Select Super View Interface"));
        bt_presenter.addActionListener(e1 -> onChooserClass(tv_presenter, ""));
        bt_activity.addActionListener(e1 -> onChooserClass(tv_activity, ""));
        bt_fragment.addActionListener(e1 -> onChooserClass(tv_fragment, ""));
        loadValues();
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e1 -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onSelectModel(AnActionEvent event, int type) {
        activityRadioButton.setSelected(type == TYPE_ACTIVITY);
        fragmentRadioButton.setSelected(type == TYPE_FRAGMENT);
        this.type = type;
    }

    private void onOK(AnActionEvent e) {
        if (TextUtils.isEmpty(textField.getText())) {
            Messages.showErrorDialog("Generation failed, " +
                            "your must enter class name",
                    "Class Name is null");
        } else {
            buttonOK.setEnabled(false);
            new CreateFile(e, textField.getText(), type).execute();
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

    private void initProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        if (projects != null && projects.length >= 1) {
            mProject = projects[0];
        } else {
            mProject = ProjectManager.getInstance().getDefaultProject();
        }
        classChooserFactory = TreeClassChooserFactory.getInstance(mProject);
        state = PropertiesComponent.getInstance(mProject);
    }

    private void loadValues() {
        tv_iview.setText(state.getValue(SUPER_VIEW));
        tv_presenter.setText(state.getValue(SUPER_PRESENTER));
        tv_activity.setText(state.getValue(SUPER_VIEW_ACTIVITY));
        tv_fragment.setText(state.getValue(SUPER_VIEW_FRAGMENT));
    }


    private void onChooserClass(JTextField jTextField, String title) {
        TreeClassChooser treeClassChooser = classChooserFactory.createProjectScopeChooser(title);
        treeClassChooser.showDialog();
        if (treeClassChooser.getSelected() != null) {
            jTextField.setText(treeClassChooser.getSelected().getQualifiedName());
            apply();
        }
    }

    private void apply() {
        state.setValue(SUPER_VIEW, tv_iview.getText());
        state.setValue(SUPER_PRESENTER, tv_presenter.getText());
        state.setValue(SUPER_VIEW_ACTIVITY, tv_activity.getText());
        state.setValue(SUPER_VIEW_FRAGMENT, tv_fragment.getText());
    }

}
