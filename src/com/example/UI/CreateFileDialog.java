package com.example.UI;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.example.CreateFile.CreateFile;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateFileDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField;
    private JRadioButton activityRadioButton;
    private JRadioButton fragmentRadioButton;
    private int type = -1;
    private final static int TYPE_ACTIVITY = 0;
    private final static int TYPE_FRAGMENT = 1;

    public CreateFileDialog(AnActionEvent e) {

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


}
