package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.*;


public class MainWindow extends JFrame {
        private JButton startButton; // Start Button
        private JButton exitButton; // Exit Button
    
        public MainWindow(Game game) {
            // Frame setup
            setTitle("HoRaYo Main Menu");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUndecorated(true); // No title bar for a cleaner look
            setResizable(false);
    
            // Fullscreen mode
            GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = graphics.getDefaultScreenDevice();
            device.setFullScreenWindow(this);
    
            // Background image
            JLabel background = new JLabel(new ImageIcon("images/MainImage.png" )); // Replace with your image path
            background.setLayout(new GridBagLayout()); // Allows components to be placed on top of the background
            add(background);
    
            // Black panel (semi-transparent)
            JPanel blackPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(0, 0, 0, 200)); // Black with transparency
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };
            blackPanel.setPreferredSize(new Dimension(500, 300));
            blackPanel.setLayout(new BoxLayout(blackPanel, BoxLayout.Y_AXIS));
            blackPanel.setOpaque(false);
    
            // Title Label
            JLabel title = new JLabel("HoRaRYo", SwingConstants.CENTER);
            try {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/drunkenhour.otf")).deriveFont(75f); // Replace with your font path
                title.setFont(customFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                title.setFont(new Font("SansSerif", Font.BOLD, 72)); // Fallback font
            }
            title.setForeground(Color.WHITE);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
    
            // Buttons
            startButton = new JButton("Start");
            exitButton = new JButton("Exit");
    
            try {
                // Load and set custom font
                Font buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/HelpMe.ttf")).deriveFont(24f); // Adjustable size
                for (JButton button : new JButton[]{startButton, exitButton}) {
                    button.setFont(buttonFont);
                    button.setFocusPainted(false);
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                    button.setAlignmentX(Component.CENTER_ALIGNMENT);
                }
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            
                // Fallback font in case of error
                Font fallbackFont = new Font("SansSerif", Font.PLAIN, 20);
                for (JButton button : new JButton[]{startButton, exitButton}) {
                    button.setFont(fallbackFont);
                    button.setFocusPainted(false);
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                    button.setAlignmentX(Component.CENTER_ALIGNMENT);
                }
            }
            
    
            exitButton.addActionListener(e -> {
                // Exit functionality placeholder
                System.exit(0);
            });
    
            // Add components to the panel
            blackPanel.add(Box.createVerticalGlue());
            blackPanel.add(title);
            blackPanel.add(Box.createVerticalStrut(20));
            blackPanel.add(startButton);
            blackPanel.add(Box.createVerticalStrut(10));
            blackPanel.add(exitButton);
            blackPanel.add(Box.createVerticalGlue());
    
            // Add black panel to background
            background.add(blackPanel);
        }
    
        // Getter for the Start button
        public JButton getStartButton() {
            return startButton;
        }
        
        // Getter for the Exit button
        public JButton getExitButton() {
            return exitButton;
        }
    }
    