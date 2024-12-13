package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageJoinRequestsView extends JFrame {

    private final JavaSocialNetworkAPI socialNetworkAPI;
    private final GroupDTO groupDTO;
    private JPanel joinRequestsPanel;
    private String navigationHandlerType = "final";
    private UserDTO userDTO;

    public ManageJoinRequestsView(JavaSocialNetworkAPI socialNetworkAPI, GroupDTO groupDTO, UserDTO userDTO) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.groupDTO = groupDTO;
        this.userDTO = userDTO;
        setupUI();
    }

    private void setupUI() {
        setTitle("Manage Join Requests");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        joinRequestsPanel = new JPanel();
        joinRequestsPanel.setLayout(new BoxLayout(joinRequestsPanel, BoxLayout.Y_AXIS));
        joinRequestsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Join Requests"));

        addJoinRequestsToPanel();

        JScrollPane scrollPane = new JScrollPane(joinRequestsPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void addJoinRequestsToPanel() {
        List<UserDTO> joinRequests = socialNetworkAPI.getGroupService().getJoinRequests(groupDTO.getGroupId());

        if (joinRequests != null) {
            for (UserDTO user : joinRequests) {

                JButton acceptButton = new JButton("Accept", 12, 12);
                JButton declineButton = new JButton("Decline", 12, 12);

                acceptButton.setFont(new Font("Arial", Font.PLAIN, 14));
                declineButton.setFont(new Font("Arial", Font.PLAIN, 14));
                acceptButton.setPreferredSize(new Dimension(100, 40));
                declineButton.setPreferredSize(new Dimension(100, 40));

                JPanel requestPanel = new JPanel(new FlowLayout());

                RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(user.getProfilePhotoPath(), 100, 100);
                profilePhotoLabel.setPreferredSize(new Dimension(100, 100));
                profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                profilePhotoLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Add navigation logic here if needed
                         NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(user, userDTO);
                         dispose();
                    }
                });

                requestPanel.add(profilePhotoLabel);
                requestPanel.add(new JLabel(user.getUsername()));
                requestPanel.add(acceptButton);
                requestPanel.add(declineButton);
                joinRequestsPanel.add(requestPanel);

                acceptButton.addActionListener(e -> {
                    socialNetworkAPI.getGroupService().approveMember(groupDTO.getGroupId(), user.getUserId());
                    joinRequestsPanel.remove(requestPanel);
                    joinRequestsPanel.revalidate();
                    joinRequestsPanel.repaint();
                    JOptionPane.showMessageDialog(this, user.getUsername() + " join request approved.");
                });
                declineButton.addActionListener(e -> {
                    socialNetworkAPI.getGroupService().declineMember(groupDTO.getGroupId(), user.getUserId());
                    joinRequestsPanel.remove(requestPanel);
                    joinRequestsPanel.revalidate();
                    joinRequestsPanel.repaint();
                    JOptionPane.showMessageDialog(this, user.getUsername() + " join request declined.");

                });
            }
        } else {
            JLabel noRequestLabel = new JLabel("No join requests found.");
            joinRequestsPanel.add(noRequestLabel);
        }
    }
}