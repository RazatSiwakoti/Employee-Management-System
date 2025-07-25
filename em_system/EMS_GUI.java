package em_system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;

/**
 * GUI implementation for the Employee Management System.
 * including adding, updating, deleting, and querying employee information.
 */

public class EMS_GUI {    
    private EMS ems;                // Reference to the backend system that handles data operations    
    private JFrame mainFrame;       // Main application window that contains all GUI components

    // Utility method to create styled buttons
    private JButton styledButton(String text, Color bgColor, Icon icon) {
        JButton button = new JButton(text, icon);
        button.setMaximumSize(new Dimension(250, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        addHoverEffect(button, bgColor, bgColor.brighter());
        return button;
    }

     // Constructs a new EMS_GUI instance with the EMS backend
    public EMS_GUI(EMS ems){
        this.ems = ems;
        initializeGUI();
    }


     // Initialize  main GUI window and cofigures the layout and event
    private void initializeGUI() {
    // Create and configure the main application window
    mainFrame = new JFrame("Employee Management System");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(600, 600);
    mainFrame.setLocationRelativeTo(null); // Center the window on screen

    // Use BoxLayout for vertical stacking
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Create and style the title label
    JLabel titleLabel = new JLabel("Employee Management System", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(titleLabel);
    mainPanel.add(Box.createVerticalStrut(10)); // Spacing

    // Subtitle: Name and Institution
    JPanel subtitlePanel = new JPanel();
    subtitlePanel.setLayout(new BoxLayout(subtitlePanel, BoxLayout.Y_AXIS));
    JLabel nameLabel = new JLabel("by RAZAT SIWAKOTI (20032655)", SwingConstants.CENTER);
    nameLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel institutionLabel = new JLabel("KOI NEWCASTLE", SwingConstants.CENTER);
    institutionLabel.setFont(new Font("Arial", Font.BOLD, 14));
    institutionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    subtitlePanel.add(nameLabel);
    subtitlePanel.add(institutionLabel);
    subtitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(subtitlePanel);
    mainPanel.add(Box.createVerticalStrut(20)); // Spacing

        // Picture: logo.jpg
    JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
    String imagePath = "C:\\Users\\razat\\Desktop\\JavaProject\\JavaProject\\em_system\\images\\logo.png";
    File imageFile = new File(imagePath);

    if (!imageFile.exists() || !imageFile.canRead()) {
        imageLabel.setText("Image not found: Check path or permissions");
        System.out.println("Image file does not exist or is not readable: " + imageFile.getAbsolutePath());
    } else {
        try {
            ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
            int newWidth = 400;
            int newHeight = 100;           
            Image scaledImage = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));                
            }
        catch (Exception e) {
            imageLabel.setText("Image not found: Error loading");
            System.out.println("Error loading image: " + e.getMessage());
        }
    }       
    
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    imageLabel.revalidate(); // Force layout update
    imageLabel.repaint(); // Force repaint
    mainPanel.add(imageLabel);
    mainPanel.add(Box.createVerticalStrut(20)); // Spacing



    // Create panel for action buttons with grid layout
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(6, 2, 10, 10)); 
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Define colors for buttons
    Color blue = new Color(0, 0, 128);
    Color green = new Color(0, 128, 0);
    Color orange = new Color(255, 140, 0);
    Color red = new Color(128, 0, 0);
    Color purple = new Color(128, 0, 128);
    Color teal = new Color(0, 128, 128);
    Color peach = new Color(255,203,164);
    Color darkGray = Color.DARK_GRAY;

    // Initialize all action buttons and add button effects
    JButton loadButton = new JButton("Load Records");
    loadButton.setBackground(blue);
    loadButton.setForeground(Color.WHITE);
    loadButton.setOpaque(true);
    loadButton.setBorderPainted(false);
    addHoverEffect(loadButton, blue, blue.brighter());

    JButton addButton = new JButton("Add Employee");
    addButton.setBackground(green);
    addButton.setForeground(Color.WHITE);
    addButton.setOpaque(true);
    addButton.setBorderPainted(false);
    addHoverEffect(addButton, green, green.brighter());

    JButton updateButton = new JButton("Update Employee");
    updateButton.setBackground(orange);
    updateButton.setForeground(Color.WHITE);
    updateButton.setOpaque(true);
    updateButton.setBorderPainted(false);
    addHoverEffect(updateButton, orange, orange.brighter());

    JButton deleteButton = new JButton("Delete Employee");
    deleteButton.setBackground(red);
    deleteButton.setForeground(Color.WHITE);
    deleteButton.setOpaque(true);
    deleteButton.setBorderPainted(false);
    addHoverEffect(deleteButton, red, red.brighter());

    JButton queryButton = new JButton("Query Employee");
    queryButton.setBackground(purple);
    queryButton.setForeground(Color.WHITE);
    queryButton.setOpaque(true);
    queryButton.setBorderPainted(false);
    addHoverEffect(queryButton, purple, purple.brighter());

    JButton displayButton = new JButton("Display All Employees");
    displayButton.setBackground(teal);
    displayButton.setForeground(Color.WHITE);
    displayButton.setOpaque(true);
    displayButton.setBorderPainted(false);
    addHoverEffect(displayButton, teal, teal.brighter());

    JButton searchButton = new JButton("Search Employee");
    searchButton.setBackground(peach);
    searchButton.setForeground(Color.WHITE);
    searchButton.setOpaque(true);
    searchButton.setBorderPainted(false);
    addHoverEffect(searchButton, peach, peach.brighter());

    JButton exitButton = new JButton("Exit");
    exitButton.setBackground(darkGray);
    exitButton.setForeground(Color.WHITE);
    exitButton.setOpaque(true);
    exitButton.setBorderPainted(false);
    addHoverEffect(exitButton, darkGray, darkGray.brighter());

    // Add buttons to panel and set up event listeners
    buttonPanel.add(loadButton);
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(queryButton);
    buttonPanel.add(displayButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(exitButton);

    // Add action listeners for each button
    loadButton.addActionListener(e -> showLoadDialog());
    addButton.addActionListener(e -> showAddDialog());
    updateButton.addActionListener(e -> showUpdateDialog());
    deleteButton.addActionListener(e -> showDeleteDialog());
    queryButton.addActionListener(e -> showQueryDialog());
    displayButton.addActionListener(e -> showDisplayDialog());
    searchButton.addActionListener(e -> showSearchDialogue());
    exitButton.addActionListener(e -> System.exit(0)); // Exit the program when exit button is clicked

    mainPanel.add(buttonPanel);

    mainFrame.add(mainPanel);
    mainFrame.setVisible(true);
}

    

    // // Reusable method to add hover effects to buttons
    private void addHoverEffect(JButton button, Color originalColor, Color hoverColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }



// 1.loading employee from the file
    private void showLoadDialog(){
        String actionFile = ems.getActionFile();
        File file = new File(actionFile);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(mainFrame, "Error: File " + actionFile + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(mainFrame, "Reloading will discard unsaved changes. Save to " + actionFile + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ems.saveToFile(actionFile);
        }
        ems.loadRecords(actionFile);
        JOptionPane.showMessageDialog(mainFrame, "Data loaded from " + actionFile, "Success", JOptionPane.INFORMATION_MESSAGE);
    }


    //2. adding new employee in the file
    private void showAddDialog(){
        // Create modal dialog for adding new employee
        JDialog addDialog = new JDialog(mainFrame, "Add Employee", true);
        addDialog.setLayout(new GridLayout(8, 2, 10, 10));
        addDialog.setSize(400, 300);
        addDialog.setLocationRelativeTo(mainFrame);

        // Initialize input components
        JLabel typeLabel = new JLabel("Employee Type:");
        // Dropdown for employee types with predefined options
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Manager", "RegularEmployee", "Intern"});
        JLabel idLabel = new JLabel("ID (Mxx / Rxx / Ixx):");
        JTextField idField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel salaryLabel = new JLabel("Base Salary:");
        JTextField salaryField = new JTextField();
        JLabel extraLabel = new JLabel("Bonus%/Allowance/Stipend:");
        JTextField extraField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        //dialog box to enter new employee details
        addDialog.add(typeLabel);
        addDialog.add(typeCombo);
        addDialog.add(idLabel);
        addDialog.add(idField);
        addDialog.add(nameLabel);
        addDialog.add(nameField);
        addDialog.add(emailLabel);
        addDialog.add(emailField);
        addDialog.add(salaryLabel);
        addDialog.add(salaryField);
        addDialog.add(extraLabel);
        addDialog.add(extraField);
        addDialog.add(addButton);
        addDialog.add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                // Get and validate input values
                String type = (String) typeCombo.getSelectedItem();
                String id = idField.getText().trim().toUpperCase();
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                double baseSalary = Double.parseDouble(salaryField.getText().trim());
                double extraBenefits = Double.parseDouble(extraField.getText().trim());

                // Validate required fields
                if (name.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(addDialog, "Name and email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate ID format based on employee type
                String idPrefix = switch (type.toLowerCase()) {
                    case "manager" -> "M";
                    case "regularemployee" -> "R";
                    case "intern" -> "I";
                    default -> "";
                };
                
                // Check ID format and uniqueness
                if (!id.startsWith(idPrefix)) {
                    JOptionPane.showMessageDialog(addDialog, "ID must start with '" + idPrefix + "' for " + type, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ems.getEmployeeById(id) != null) {
                    JOptionPane.showMessageDialog(addDialog, "Employee ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create appropriate employee object based on type
                Employee emp;
                switch (type.toLowerCase()) {
                    case "manager":
                        emp = new Manager(id, name, email, baseSalary, 2.5, extraBenefits);
                        break;
                    case "regularemployee":
                        emp = new RegularEmployee(id, name, email, baseSalary, 2.5, extraBenefits);
                        break;
                    case "intern":
                        emp = new Intern(id, name, email, baseSalary, 2.5, extraBenefits);
                        break;
                    default:
                        JOptionPane.showMessageDialog(addDialog, "Invalid employee type.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Add employee to system and save changes
                ems.getEmployees().add(emp);
                ems.getEmployeesByID().put(id, emp);
                ems.saveToFile(ems.getActionFile());
                JOptionPane.showMessageDialog(addDialog, "Employee added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                addDialog.dispose();
            } catch (NumberFormatException ex) {
                // Handle invalid number input
                JOptionPane.showMessageDialog(addDialog, "Invalid number format: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> addDialog.dispose());

        addDialog.setVisible(true); 
    }

  
    //3. Dialog box for updating current employee in the file
    private void showUpdateDialog(){
        JDialog updateDialog = new JDialog(mainFrame, "Update Employee", true);
        updateDialog.setLayout(new GridLayout(3, 2, 10, 10));
        updateDialog.setSize(400, 150);
        updateDialog.setLocationRelativeTo(mainFrame);

        JLabel idLabel = new JLabel("Enter Employee ID:");
        JTextField idField = new JTextField();
        JButton findButton = new JButton("Find Employee");
        updateDialog.add(idLabel);
        updateDialog.add(idField);
        updateDialog.add(new JLabel(""));
        updateDialog.add(findButton);

        findButton.addActionListener(e -> {
            String id = idField.getText().trim().toUpperCase();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(updateDialog, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Employee emp = ems.getEmployeeById(id);
            if (emp == null) {
                JOptionPane.showMessageDialog(updateDialog, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            updateDialog.dispose();
            showUpdateOptionsDialog(emp);
        });

        updateDialog.setVisible(true);
    }

   
    //Dialog for different update option for specific employee
    private void showUpdateOptionsDialog(Employee emp){
        // Create dialog for update options
        JDialog optionsDialog = new JDialog(mainFrame, "Update Options for " + emp.getName(), true);
        optionsDialog.setLayout(new GridLayout(10, 1, 10, 10));
        optionsDialog.setSize(400, 400);
        optionsDialog.setLocationRelativeTo(mainFrame);

        // Display current employee information
        JLabel infoLabel = new JLabel(emp.toString());
        optionsDialog.add(infoLabel);  // Add the label to display employee info
        
        
        
        // Initialize update option buttons
        JButton nameButton = new JButton("Update Name");
        JButton emailButton = new JButton("Update Email");
        JButton salaryButton = new JButton("Update Base Salary");
        JButton ratingButton = new JButton("Update Performance Rating");
        JButton warningButton = new JButton("Issue Warning");
        JButton appreciationButton = new JButton("Issue Appreciation");
        JButton bonusButton = new JButton("Apply Bonus");
        JButton fineButton = new JButton("Apply Fine");
        JButton cancelButton = new JButton("Cancel");
        optionsDialog.add(nameButton);
        optionsDialog.add(emailButton);
        optionsDialog.add(salaryButton);
        optionsDialog.add(ratingButton);
        optionsDialog.add(warningButton);
        optionsDialog.add(appreciationButton);
        optionsDialog.add(bonusButton);
        optionsDialog.add(fineButton);
        optionsDialog.add(cancelButton);



        // Add action listeners for each update option
        nameButton.addActionListener(e -> {
            // Handle name update
            String newName = JOptionPane.showInputDialog(optionsDialog, "Enter new name:");
            if (newName != null && !newName.trim().isEmpty()) {
                emp.setName(newName);
                ems.saveToFile(ems.getActionFile());
                JOptionPane.showMessageDialog(optionsDialog, "Name updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(optionsDialog, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        emailButton.addActionListener(e -> {
            String newEmail = JOptionPane.showInputDialog(optionsDialog, "Enter new email:");
            if (newEmail != null && !newEmail.trim().isEmpty()) {
                emp.setEmail(newEmail);
                ems.saveToFile(ems.getActionFile());
                JOptionPane.showMessageDialog(optionsDialog, "Email updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(optionsDialog, "Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        salaryButton.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog(optionsDialog, "Enter new base salary:");
                if (input != null && !input.trim().isEmpty()) {
                    double newSalary = Double.parseDouble(input);
                    if (newSalary < 0) {
                        JOptionPane.showMessageDialog(optionsDialog, "Base salary cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    emp.setBaseSalary(newSalary);
                    ems.saveToFile(ems.getActionFile());
                    JOptionPane.showMessageDialog(optionsDialog, "Base salary updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    infoLabel.setText(emp.toString()); // Update displayed info
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(optionsDialog, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ratingButton.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog(optionsDialog, "Enter new performance rating (0.0-5.0):");
                if (input != null) {
                    double newRating = Double.parseDouble(input);
                    if (newRating >= 0.0 && newRating <= 5.0) {
                        emp.getPerformance().setPerformanceRating(newRating);
                        ems.saveToFile(ems.getActionFile());
                        JOptionPane.showMessageDialog(optionsDialog, "Performance rating updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(optionsDialog, "Rating must be between 0.0 and 5.0.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(optionsDialog, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        warningButton.addActionListener(e -> {
            emp.getPerformance().addWarning();
            ems.saveToFile(ems.getActionFile());
            JOptionPane.showMessageDialog(optionsDialog, "Warning issued.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        appreciationButton.addActionListener(e -> {
            emp.getPerformance().addAppreciationLetter();
            ems.saveToFile(ems.getActionFile());
            JOptionPane.showMessageDialog(optionsDialog, "Appreciation letter issued.", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        bonusButton.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog(optionsDialog, "Enter bonus amount:");
                if (input != null && !input.trim().isEmpty()) {
                    double bonus = Double.parseDouble(input);
                    if (bonus < 0) {
                        JOptionPane.showMessageDialog(optionsDialog, "Bonus cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    emp.getSalary().applyBonus(bonus);
                    ems.saveToFile(ems.getActionFile());
                    JOptionPane.showMessageDialog(optionsDialog, "Bonus applied.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    infoLabel.setText(emp.toString()); // Update displayed info
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(optionsDialog, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        fineButton.addActionListener(e -> {
            try {
                String input = JOptionPane.showInputDialog(optionsDialog, "Enter fine amount:");
                if (input != null && !input.trim().isEmpty()) {
                    double fine = Double.parseDouble(input);
                    if (fine < 0) {
                        JOptionPane.showMessageDialog(optionsDialog, "Fine cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    emp.getSalary().applyFine(fine);
                    ems.saveToFile(ems.getActionFile());
                    JOptionPane.showMessageDialog(optionsDialog, "Fine applied.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    infoLabel.setText(emp.toString()); // Update displayed info
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(optionsDialog, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> optionsDialog.dispose());

        optionsDialog.setVisible(true);
    }


   // 4. Delete employee dialog box
    private void showDeleteDialog(){
        String id = JOptionPane.showInputDialog(mainFrame, "Enter Employee ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Employee emp = ems.getEmployeeById(id.toUpperCase());
        if (emp == null) {
            JOptionPane.showMessageDialog(mainFrame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(mainFrame, "Delete " + emp.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ems.getEmployees().remove(emp);
            ems.getEmployeesByID().remove(emp.getId());
            ems.saveToFile(ems.getActionFile());
            JOptionPane.showMessageDialog(mainFrame, emp.getName() + " deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

   // 5. Query employee dialog box
   private void showQueryDialog() {
    JDialog queryDialog = new JDialog(mainFrame, "Query Employee", true);
    queryDialog.setSize(350, 280);
    queryDialog.setLocationRelativeTo(mainFrame);

    // Main panel with vertical layout and padding
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Buttons with icons
    JButton idButton = styledButton("Query by ID", new Color(52, 152, 219), UIManager.getIcon("OptionPane.questionIcon"));
    JButton nameButton = styledButton("Query by Name", new Color(46, 204, 113), UIManager.getIcon("FileView.directoryIcon"));
    JButton ratingButton = styledButton("Query by Rating", new Color(155, 89, 182), UIManager.getIcon("FileChooser.detailsViewIcon"));
    JButton cancelButton = styledButton("Cancel", Color.DARK_GRAY, UIManager.getIcon("OptionPane.errorIcon"));

    panel.add(idButton);
    panel.add(Box.createVerticalStrut(10));
    panel.add(nameButton);
    panel.add(Box.createVerticalStrut(10));
    panel.add(ratingButton);
    panel.add(Box.createVerticalStrut(10));
    panel.add(cancelButton);

    queryDialog.add(panel);

    // Action listeners
    idButton.addActionListener(e -> {
        queryDialog.dispose();
        String id = JOptionPane.showInputDialog(mainFrame, "Enter ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee emp = ems.getEmployeeById(id.toUpperCase());

        if (emp != null) {
            showEmployeeDetails(emp);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Employee ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    });


    nameButton.addActionListener(e -> {
        queryDialog.dispose();
        String name = JOptionPane.showInputDialog(mainFrame, "Enter Name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Employee> byName = ems.getEmployees().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (byName.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No employees found.", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            showEmployeesInTable(byName, "Employees Found");
        }
    });

    ratingButton.addActionListener(e -> {
        queryDialog.dispose();
        try {
            String input = JOptionPane.showInputDialog(mainFrame, "Enter minimum performance rating (0.0–5.0):");
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Rating cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double rating = Double.parseDouble(input);
            if (rating < 0.0 || rating > 5.0) {
                JOptionPane.showMessageDialog(mainFrame, "Rating must be between 0.0 and 5.0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Employee> byRating = ems.getEmployees().stream()
                    .filter(emp -> emp.getPerformance().getPerformanceRating() >= rating)
                    .collect(Collectors.toList());

                    
            if (byRating.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "No employees found.", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                showEmployeesInTable(byRating, "Employees with Rating ≥ " + rating);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    cancelButton.addActionListener(e -> queryDialog.dispose());

    queryDialog.setVisible(true);
}


    //6. Display ALl employee data in table 
    private void showDisplayDialog() {
        // Check for empty employees
        if (ems.getEmployees().isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "No employees found in the system.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Create dialog
        JDialog displayDialog = new JDialog(mainFrame, "All Employees", true);
        displayDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        displayDialog.setSize(950, 450);
        displayDialog.setLocationRelativeTo(mainFrame);
        displayDialog.setLayout(new BorderLayout(10, 10));
    
        // Create table model
        String[] columns = {"Type", "ID", "Name", "Email", "Base Salary", "Performance Rating", "Extra Benefits", "Total Salary"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
    
        // Populate table initially
        for (Employee emp : ems.getEmployees()) {
            String type;
            double extra = 0.0;
    
            if (emp instanceof Manager) {
                type = "Manager";
                extra = ((Manager) emp).getBonusPercentage();
            } else if (emp instanceof RegularEmployee) {
                type = "RegularEmployee";
                extra = ((RegularEmployee) emp).getAllownace(); // Fixed typo
            } else if (emp instanceof Intern) {
                type = "Intern";
                extra = ((Intern) emp).getStipend();
            } else {
                type = "Unknown";
            }
    
            double totalSalary = emp.getBaseSalary() + extra;
    
            tableModel.addRow(new Object[]{
                type,
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                String.format("%.2f", emp.getBaseSalary()),
                emp.getPerformance().getPerformanceRating(),
                String.format("%.2f", extra),
                String.format("%.2f", totalSalary)
            });
        }
    
        // Create table with custom renderer for row colors
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                String employeeType = (String) getModel().getValueAt(row, 0);
    
                // Set background color based on type
                if ("Manager".equals(employeeType)) {
                    c.setBackground(new Color(198, 239, 206)); // Light green
                } else if ("RegularEmployee".equals(employeeType)) {
                    c.setBackground(new Color(221, 235, 247)); // Light blue
                } else if ("Intern".equals(employeeType)) {
                    c.setBackground(new Color(255, 229, 204)); // Light orange
                } else {
                    c.setBackground(Color.WHITE); // Default white
                }
    
                // For selection highlight
                if (isCellSelected(row, column)) {
                    c.setBackground(c.getBackground().darker());
                }
    
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        displayDialog.add(scrollPane, BorderLayout.CENTER);
    
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        // Sort button
        JButton sortButton = new JButton("Sort");
        sortButton.setBackground(new Color(0, 120, 215)); // Blue, like Load
        sortButton.setForeground(Color.WHITE);
        sortButton.setOpaque(true);
        sortButton.setBorderPainted(false);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create sort dialog
                JDialog sortDialog = new JDialog(displayDialog, "Sort Employees", true);
                sortDialog.setSize(300, 200);
                sortDialog.setLocationRelativeTo(displayDialog);
                sortDialog.setLayout(new GridLayout(3, 2, 10, 10));
    
                // Field selection
                JLabel fieldLabel = new JLabel("Sort by:");
                JComboBox<String> fieldCombo = new JComboBox<>(new String[]{"ID", "Name", "Salary", "Performance Rating"});
                sortDialog.add(fieldLabel);
                sortDialog.add(fieldCombo);
    
                // Direction selection
                JLabel directionLabel = new JLabel("Direction:");
                JComboBox<String> directionCombo = new JComboBox<>(new String[]{"Ascending", "Descending"});
                sortDialog.add(directionLabel);
                sortDialog.add(directionCombo);
    
                // OK button
                JButton okButton = new JButton("OK");
                okButton.setBackground(new Color(0, 128, 0)); // Green, like Add
                okButton.setForeground(Color.WHITE);
                okButton.setOpaque(true);
                okButton.setBorderPainted(false);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get selected options
                        String field = fieldCombo.getSelectedItem().toString().toLowerCase();
                        if (field.equals("performance rating")) {
                            field = "rating"; // Map to backend field
                        }
                        boolean ascending = directionCombo.getSelectedItem().equals("Ascending");
    
                        // Sort employees
                        ems.sortEmployees(field, ascending);
    
                        // Update table with all columns
                        tableModel.setRowCount(0); // Clear table
                        for (Employee emp : ems.getEmployees()) {
                            String type;
                            double extra = 0.0;
    
                            if (emp instanceof Manager) {
                                type = "Manager";
                                extra = ((Manager) emp).getBonusPercentage();
                            } else if (emp instanceof RegularEmployee) {
                                type = "RegularEmployee";
                                extra = ((RegularEmployee) emp).getAllownace(); // Fixed typo
                            } else if (emp instanceof Intern) {
                                type = "Intern";
                                extra = ((Intern) emp).getStipend();
                            } else {
                                type = "Unknown";
                            }
    
                            double totalSalary = emp.getBaseSalary() + extra;
    
                            tableModel.addRow(new Object[]{
                                type,
                                emp.getId(),
                                emp.getName(),
                                emp.getEmail(),
                                String.format("%.2f", emp.getBaseSalary()),
                                emp.getPerformance().getPerformanceRating(),
                                String.format("%.2f", extra),
                                String.format("%.2f", totalSalary)
                            });
                        }
    
                        sortDialog.dispose();
                    }
                });
    
                // Cancel button
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setBackground(Color.DARK_GRAY); // Gray, like Exit
                cancelButton.setForeground(Color.WHITE);
                cancelButton.setOpaque(true);
                cancelButton.setBorderPainted(false);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sortDialog.dispose();
                    }
                });
    
                sortDialog.add(okButton);
                sortDialog.add(cancelButton);
                sortDialog.setVisible(true);
            }
        });
    
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.DARK_GRAY); // Gray, like Exit
        closeButton.setForeground(Color.WHITE);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDialog.dispose();
            }
        });
    
        buttonPanel.add(sortButton);
        buttonPanel.add(closeButton);
        displayDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        displayDialog.setVisible(true);
    }

    

    //Display Single Eployee
    private void showEmployeeDetails(Employee emp) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        double extra = 0.0;
        String department = "Unknown";  // Initialize with default value
        if (emp instanceof Manager) {
            extra = ((Manager) emp).getBonusPercentage();
            department = "Manager";
        } else if (emp instanceof RegularEmployee) {
            extra = ((RegularEmployee) emp).getAllownace();
            department = "Regular";
        } else if (emp instanceof Intern) {
            extra = ((Intern) emp).getStipend();
            department = "Intern";
        }

        panel.add(new JLabel("🆔 ID: " + emp.getId()));
        panel.add(new JLabel("👤 Name: " + emp.getName()));
        panel.add(new JLabel("🏢 Type: " + department));    
        panel.add(new JLabel("📧 Email: " + emp.getEmail()));
        panel.add(new JLabel("⭐ Performance Rating: " + emp.getPerformance().getPerformanceRating()));
        panel.add(new JLabel("💰 Base Salary: " + emp.getBaseSalary()));
        panel.add(new JLabel("🎁 Extra Benefits: " + extra));
        panel.add(new JLabel("📊 Total Salary: " + emp.calculateSalary()));    
        JOptionPane.showMessageDialog(mainFrame, panel, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    //Employee Table
    private void showEmployeesInTable(List<Employee> employees, String title) {
    String[] columnNames = {"ID", "👤 Name","🏢 Department", "📧 Email", " 💰 Base Salary", " ⭐ Rating", " 🎁 Extra Benefits", " 📊 Total Salary"};
    Object[][] rowData = new Object[employees.size()][columnNames.length];

    for (int i = 0; i < employees.size(); i++) {
        Employee emp = employees.get(i);
        double extra = 0.0;
        String department = "Unknown";

        if (emp instanceof Manager) {
            extra = ((Manager) emp).getBonusPercentage();
            department = "Manager";
        } else if (emp instanceof RegularEmployee) {
            extra = ((RegularEmployee) emp).getAllownace();
            department = "Regular";
        } else if (emp instanceof Intern) {
            extra = ((Intern) emp).getStipend();
            department = "Intern";
        }

        rowData[i][0] = emp.getId();
        rowData[i][1] = emp.getName();
        rowData[i][2] = department;
        rowData[i][3] = emp.getEmail();
        rowData[i][4] = emp.getBaseSalary();
        rowData[i][5] = emp.getPerformance().getPerformanceRating();
        rowData[i][6] = String.format("%.2f", extra);
        rowData[i][7] = emp.calculateSalary();
    }

    JTable table = new JTable(rowData, columnNames); 
    table.setFillsViewportHeight(true);
    table.setEnabled(false);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(800, 400)); // Set preferred size here

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(scrollPane, BorderLayout.CENTER);

    JDialog dialog = new JDialog(mainFrame, title, true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.getContentPane().add(panel);
    dialog.setSize(850, 450); // Set bigger size for the dialog window
    dialog.setLocationRelativeTo(mainFrame);
    dialog.setVisible(true);

   
}






    //7. Seacrh Employee
    private void showSearchDialogue(){
    // First dialog: Choose field
    String[] fields = {"ID", "Name"};
    String field = (String) JOptionPane.showInputDialog(
        mainFrame,
        "Search by:",
        "Search Employee",
        JOptionPane.QUESTION_MESSAGE,
        null,
        fields,
        fields[0]
    );
    if (field == null) {
        return; // Cancelled
    }
    field = field.toLowerCase();

    String searchType = "linear"; // Default for name
    if (field.equals("id")) {
        // Second dialog: Choose linear or binary for ID
        String[] types = {"Linear", "Binary"};
        searchType = (String) JOptionPane.showInputDialog(
            mainFrame,
            "Search type for ID:",
            "Search Employee",
            JOptionPane.QUESTION_MESSAGE,
            null,
            types,
            types[0]
        );
        if (searchType == null) {
            return; // Cancelled
        }
        searchType = searchType.toLowerCase();
    }

    // Third dialog: Enter search value
    String value = JOptionPane.showInputDialog(
        mainFrame,
        "Enter " + field + " to search:",
        "Search Employee",
        JOptionPane.QUESTION_MESSAGE
    );
    if (value == null) {
        return; // Cancelled
    }
    value = value.trim();

    // Create result dialog
    JDialog resultDialog = new JDialog(mainFrame, "Search Results", true);
    resultDialog.setSize(500, 400);
    resultDialog.setLocationRelativeTo(mainFrame);
    resultDialog.setLayout(new BorderLayout(10, 10));

    // Table for results
    String[] columns = {"ID", "Name", "Department"};
    DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Read-only
        }
    };
    JTable table = new JTable(tableModel);
    table.setRowHeight(25);
    table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    JScrollPane scrollPane = new JScrollPane(table);
    resultDialog.add(scrollPane, BorderLayout.CENTER);

    // Time label
    JLabel timeLabel = new JLabel("Search time: 0.000 ms");
    JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    timePanel.add(timeLabel);
    resultDialog.add(timePanel, BorderLayout.SOUTH);

    // Perform search
    SearchResult result;
    if (field.equals("id") && searchType.equals("binary")) {
        result = SearchResult.searchEmployees(ems.getEmployees(), field, value); // Binary
    } else {
        result = SearchResult.searchEmployees(ems.getEmployees(), field, value); // Linear
    }

    // Update table
    tableModel.setRowCount(0);
    for (Employee emp : result.getEmployees()) {
        tableModel.addRow(new Object[]{
            emp.getId(),
            emp.getName(),
            emp instanceof Manager ? "Manager" : 
            emp instanceof RegularEmployee ? "Regular" : 
            emp instanceof Intern ? "Intern" : "Unknown"
        });
    }

    // Update time
    timeLabel.setText(String.format("Search time: %.3f ms", result.getTimeMs()));

    // Close button
    JButton closeButton = new JButton("Close");
    closeButton.setBackground(Color.DARK_GRAY);
    closeButton.setForeground(Color.WHITE);
    closeButton.setOpaque(true);
    closeButton.setBorderPainted(false);
    closeButton.addActionListener(e -> resultDialog.dispose());
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(closeButton);
    resultDialog.add(buttonPanel, BorderLayout.NORTH);

    resultDialog.setVisible(true);
}

}



