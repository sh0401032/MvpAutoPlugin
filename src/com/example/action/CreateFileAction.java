package com.example.action;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.util.IconLoader;
import com.example.UI.CreateFileDialog;

/**
 * Created by example on 17/7/25.
 */
public class CreateFileAction extends AnAction {

    public CreateFileAction() {
        getTemplatePresentation().setIcon(IconLoader.getIcon("/icons/icon_tf.png"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new CreateFileDialog(e).setVisible(true);
    }

    @Override
    public void update(AnActionEvent e) {
//        super.update(e);
//
        IdeView ideView = e.getRequiredData(LangDataKeys.IDE_VIEW);
//        Messages.showErrorDialog(ideView.toString(),"Error");

        if (ideView.getDirectories().length == 1) {
            e.getPresentation().setEnabledAndVisible(true);
        } else {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }
}
