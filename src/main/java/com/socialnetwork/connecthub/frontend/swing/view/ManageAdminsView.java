package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.service.java.JavaGroupService;
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

public class ManageAdminsView extends JFrame {
    private JTabbedPane tabbedPane;
    private GroupDTO groupDTO;
    private SocialNetworkAPI socialNetworkAPI;
    private String navigationHandlerType = "final";
    private UserDTO userDTO;
    public ManageAdminsView(SocialNetworkAPI socialNetworkAPI, GroupDTO groupDTO, UserDTO userDTO) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.groupDTO = groupDTO;
        this.userDTO = userDTO;
        setupUI();
    }

    private void setupUI() {
        setTitle("Manage Group Admins");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        tabbedPane.removeAll();

        setupPromoteMemberPanel();
        setupDemoteAdminPanel();

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    private void setupPromoteMemberPanel() {
        JPanel promoteMemberPanel = new JPanel();
        promoteMemberPanel.setLayout(new BoxLayout(promoteMemberPanel, BoxLayout.Y_AXIS));
        promoteMemberPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Promote Members"));

        List<UserDTO> members = socialNetworkAPI.getGroupService().getGroupMembers(groupDTO.getGroupId());
        for (UserDTO member : members) {
            if(socialNetworkAPI.getGroupService().getMembershipType(groupDTO.getGroupId(), member.getUserId()) == JavaGroupService.MembershipType.MEMBER){
                JButton promoteButton = new JButton("Promote to Admin", 12, 12);
                promoteButton.setFont(new Font("Arial", Font.PLAIN, 14));
                promoteButton.setPreferredSize(new Dimension(300, 40));

                JPanel memberPanel = new JPanel(new FlowLayout());

                RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(member.getProfilePhotoPath(), 100, 100);
                profilePhotoLabel.setPreferredSize(new Dimension(100, 100));
                profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                profilePhotoLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                         NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(member, userDTO);
                         dispose();
                    }
                });

                memberPanel.add(profilePhotoLabel);
                memberPanel.add(new JLabel(member.getUsername()));
                memberPanel.add(promoteButton);
                promoteMemberPanel.add(memberPanel);
                promoteButton.addActionListener(e -> {
                    socialNetworkAPI.getGroupService().promoteToAdmin(groupDTO.getGroupId(), member.getUserId());
                    promoteMemberPanel.remove(memberPanel);
                    promoteMemberPanel.revalidate();
                    promoteMemberPanel.repaint();
                    JOptionPane.showMessageDialog(this, member.getUsername() + " promoted to admin.");
                });
            }
        }
        tabbedPane.addTab("Promote Member", new JScrollPane(promoteMemberPanel));
    }
    private void setupDemoteAdminPanel() {
        JPanel demoteAdminPanel = new JPanel();
        demoteAdminPanel.setLayout(new BoxLayout(demoteAdminPanel, BoxLayout.Y_AXIS));
        demoteAdminPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Demote Admins"));

        List<UserDTO> admins = socialNetworkAPI.getGroupService().getGroupAdmins(groupDTO.getGroupId());

        for(UserDTO admin : admins) {
            if(socialNetworkAPI.getGroupService().getMembershipType(groupDTO.getGroupId(), admin.getUserId()) == JavaGroupService.MembershipType.ADMIN){
                JButton demoteButton = new JButton("Demote Admin", 12, 12);
                demoteButton.setFont(new Font("Arial", Font.PLAIN, 14));
                demoteButton.setPreferredSize(new Dimension(300, 40));


                JPanel adminPanel = new JPanel(new FlowLayout());

                RoundedImageLabel profilePhotoLabel = new RoundedImageLabel(admin.getProfilePhotoPath(), 100, 100);
                profilePhotoLabel.setPreferredSize(new Dimension(100, 100));
                profilePhotoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                profilePhotoLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Add navigation logic here if needed
                        NavigationHandlerFactory.getNavigationHandler(navigationHandlerType).goToProfileView(admin, userDTO);
                        dispose();
                    }
                });
                adminPanel.add(profilePhotoLabel);
                adminPanel.add(new JLabel(admin.getUsername()));
                adminPanel.add(demoteButton);
                demoteAdminPanel.add(adminPanel);
                demoteButton.addActionListener(e -> {
                    socialNetworkAPI.getGroupService().demoteAdmin(groupDTO.getGroupId(), admin.getUserId());
                    demoteAdminPanel.remove(adminPanel);
                    demoteAdminPanel.revalidate();
                    demoteAdminPanel.repaint();
                    JOptionPane.showMessageDialog(this, admin.getUsername() + " demoted from admin.");
                });
            }
        }
        tabbedPane.addTab("Demote Admin", new JScrollPane(demoteAdminPanel));
    }
}