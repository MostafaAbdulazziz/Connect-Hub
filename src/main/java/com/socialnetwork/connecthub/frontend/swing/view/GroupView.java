package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.model.Group;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class GroupView extends View {
    SocialNetworkAPI socialNetworkAPI;
    GroupDTO group;
    UserDTO user;
    JPanel panel;
    private final String navigationHandlerType = "final";
    public GroupView(SocialNetworkAPI socialNetworkAPI, GroupDTO groupDTO, UserDTO userDTO) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.group = groupDTO;
        this.user = userDTO;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Prevent default behavior

        // Add a custom window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JavaUserAccountService.getInstance().logout(user.getUserId());
                dispose();
                NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToLoginView();
            }
        });
        panel = new JPanel(null);
        panel.setLayout(null);
        panel.setBounds(0, 0, 1800, 800);
        panel.setBackground(new Color(215, 215, 215));
        add(panel);

    }

}
