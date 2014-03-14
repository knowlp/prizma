package com.hrzafer.prizma.gui;


import com.hrzafer.prizma.ArffCreator;
import com.hrzafer.prizma.ArffProperties;
import com.hrzafer.prizma.Config;
import com.hrzafer.prizma.data.FeatureReader;
import com.hrzafer.prizma.feature.Feature;
import com.hrzafer.prizma.util.GUI;
import com.hrzafer.prizma.util.STR;
import com.hrzafer.prizma.util.Timer;
import com.hrzafer.prizma.util.WekaHelper;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class Frame extends javax.swing.JFrame {

    private DefaultListModel _unselectedAttributeListModel;
    private DefaultListModel _selectedAttributeListModel;

    @SuppressWarnings("LeakingThisInConstructor")
    public Frame() {
        initComponents();
        initAttributeLists();
        populateUnselectedAttributesList();
        GUI.setLookandFeel(GUI.SYSTEM, this);
        initTextBoxes();
    }

    private void initTextBoxes(){

        String lastDatasetDir = Config.get("lastDatasetDir");
        String lastDataset = Config.get("lastDataset");
        datasetDirectoryTextField.setText(lastDatasetDir + "\\" + lastDataset);
        updateArffFileNameTextFieldByDatasetDirectoryName();
    }

    private void initAttributeLists() {
        _unselectedAttributeListModel = new DefaultListModel();
        _selectedAttributeListModel = new DefaultListModel();
        unselectedAttributeList.setModel(_unselectedAttributeListModel);
        selectedAttributeList.setModel(_selectedAttributeListModel);
    }

    private void populateUnselectedAttributesList() {
        List<Feature> features = getInstancesOfAllAttributes();
        addAttributesAsElementsToListModel(_unselectedAttributeListModel, features);
        updateAttributeCountLabels();
    }

    private void addAttributesAsElementsToListModel(DefaultListModel listModel, List<Feature> features) {
        for (Feature feature : features) {
            listModel.addElement(feature);
        }
    }

    private List<Feature> getInstancesOfAllAttributes() {
        List<Feature> features = Collections.EMPTY_LIST;
        try {
            String featuresXmlPath = Config.get("prizma.features");
            features = FeatureReader.read(featuresXmlPath);
        } catch (RuntimeException e) {
            GUI.messageBox("The file " + STR.addDoubleQuote("catAdult/features.xml")
                    + " must be in the same directory with "
                    + STR.addDoubleQuote("prizma.jar"), "Applicatin can not be started!");
            closeTheApplication();
            e.printStackTrace();
        }
        return features;
    }

    public void closeTheApplication() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }

    private void updateAttributeCountLabels() {
        selectedAttributeCountLabel.setText("Count: " + _selectedAttributeListModel.getSize());
        unselectedAttributeCountLabel.setText("Count: " + _unselectedAttributeListModel.getSize());
    }

    private ArffProperties getArffProperties() {
        List<Feature> features = getSelectedAttributes();
        String datasetDirectoryPath = getDatasetDirectoryPath();
        String relation = getArffFileNameFromDatasetDirectoryPath();
        double trainPercentage = getTrainPercentageForCreation();
        boolean unicode = unicodeCheckBox.isSelected();
        boolean random = randomizeCheckBox.isSelected();
        return new ArffProperties(datasetDirectoryPath, features, relation, trainPercentage, unicode, random);
    }

    private boolean IsUserInputValid() {
        double trainPercentage;
        try {
            trainPercentage = getTrainPercentageForCreation();
        } catch (NumberFormatException ex) {
            GUI.messageBox("Please enter a valid train percentage value", "Error");
            return false;
        }
        if (getDatasetDirectoryPath().isEmpty()) {
            GUI.messageBox("Please select a data directory", "Error");
            return false;
        } else if (getArffFileWritePath().isEmpty()) {
            GUI.messageBox("Please enter a file name without arff extension", "Error");
            return false;
        } else if (trainPercentage < 0 || trainPercentage > 100) {
            GUI.messageBox("Percentage value must be with in the range 0 and 100", "Error");
            return false;
        } else if (_selectedAttributeListModel.isEmpty()) {
            GUI.messageBox("Please select at least one Feature", "Error");
            return false;
        }
        return true;
    }

    private String getArffFileWritePath() {
        return arffFileWritePathTextField.getText().replace("\\", "/");
    }

    private String getArffFileReadPath() {
        return arffFileReadPathTextField.getText().replace("\\", "/");
    }

    private String getDatasetDirectoryPath() {
        return datasetDirectoryTextField.getText().replace("\\", "/");
    }

    private double getTrainPercentageForCreation() {
        return Double.parseDouble(trainPercentageForCreationTextField.getText());
    }

    private List<Feature> getSelectedAttributes() {
        return getListModelElementsAsAttributes(_selectedAttributeListModel);
    }

    private double getTrainPercentageForEvaluation() {
        return Double.parseDouble(trainPercentageForEvaluationTextField.getText());
    }

    private String getClassifierName() {
        String name = classifierComboBox.getSelectedItem().toString();
        return name.replaceAll(" ", "");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        mainTabbedPane = new javax.swing.JTabbedPane();
        preprocessTabPanel = new javax.swing.JPanel();
        selectDataDirectoryPanel = new javax.swing.JPanel();
        datasetDirectoryTextField = new javax.swing.JTextField();
        browseDatasetDirectoryButton = new javax.swing.JButton();
        arffFileWritePathTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        createArffButton = new javax.swing.JButton();
        trainPercentageForCreationTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        randomizeCheckBox = new javax.swing.JCheckBox();
        unicodeCheckBox = new javax.swing.JCheckBox();
        selectAttributePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        unselectedAttributeList = new javax.swing.JList();
        selectAllAttributesButton = new javax.swing.JButton();
        unselectAllAttributesButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        selectedAttributeList = new javax.swing.JList();
        selectAttributeButton = new javax.swing.JButton();
        unselectAttributeButton = new javax.swing.JButton();
        unselectedAttributeCountLabel = new javax.swing.JLabel();
        selectedAttributeCountLabel = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        experimentTabPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        arffFilePathLabel = new javax.swing.JLabel();
        arffFileReadPathTextField = new javax.swing.JTextField();
        browseArffFileButton = new javax.swing.JButton();
        trainPercentageLabel = new javax.swing.JLabel();
        trainPercentageForEvaluationTextField = new javax.swing.JTextField();
        classifyButton = new javax.swing.JButton();
        classifierLabel = new javax.swing.JLabel();
        classifierComboBox = new javax.swing.JComboBox();
        evaluationPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        evaluationTextArea = new javax.swing.JTextArea();
        aboutTabPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        aboutTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prizma");
        setName("frame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainTabbedPane.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                mainTabbedPaneFocusGained(evt);
            }
        });

        preprocessTabPanel.setMaximumSize(new java.awt.Dimension(800, 500));

        selectDataDirectoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Data Directory"));

        datasetDirectoryTextField.setText("dataset_polarity_train");
        datasetDirectoryTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                datasetDirectoryTextFieldKeyReleased(evt);
            }
        });

        browseDatasetDirectoryButton.setText("Browse");
        browseDatasetDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseDatasetDirectoryButtonActionPerformed(evt);
            }
        });

        arffFileWritePathTextField.setText("relation_polarity_train");

        jLabel3.setText(".arff");

        createArffButton.setText("Create");
        createArffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createArffButtonActionPerformed(evt);
            }
        });

        trainPercentageForCreationTextField.setText("100");

        jLabel1.setText("Arff File Name:");

        jLabel2.setText("Train %:");

        jLabel5.setText("Dataset directory: ");

        randomizeCheckBox.setText("randomize");
        randomizeCheckBox.setToolTipText("Check this for randomly distributed Test and Train instances");

        unicodeCheckBox.setSelected(true);
        unicodeCheckBox.setText("unicode");
        unicodeCheckBox.setToolTipText("Uncheck this if the text files are not UTF-8 but ANSI");

        javax.swing.GroupLayout selectDataDirectoryPanelLayout = new javax.swing.GroupLayout(selectDataDirectoryPanel);
        selectDataDirectoryPanel.setLayout(selectDataDirectoryPanelLayout);
        selectDataDirectoryPanelLayout.setHorizontalGroup(
            selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                        .addComponent(randomizeCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(unicodeCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                        .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                                .addComponent(arffFileWritePathTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(trainPercentageForCreationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(datasetDirectoryTextField))
                        .addGap(18, 18, 18)
                        .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(createArffButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(browseDatasetDirectoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))))
                .addContainerGap())
        );
        selectDataDirectoryPanelLayout.setVerticalGroup(
            selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectDataDirectoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datasetDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseDatasetDirectoryButton)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arffFileWritePathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(trainPercentageForCreationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createArffButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(selectDataDirectoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(randomizeCheckBox)
                    .addComponent(unicodeCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        selectAttributePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Feature"));

        jScrollPane2.setViewportView(unselectedAttributeList);

        selectAllAttributesButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        selectAllAttributesButton.setText(">>");
        selectAllAttributesButton.setToolTipText("Select all");
        selectAllAttributesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllAttributesButtonActionPerformed(evt);
            }
        });

        unselectAllAttributesButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        unselectAllAttributesButton.setText("<<");
        unselectAllAttributesButton.setToolTipText("Unselect all");
        unselectAllAttributesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unselectAllAttributesButtonActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(selectedAttributeList);

        selectAttributeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        selectAttributeButton.setText(">");
        selectAttributeButton.setToolTipText("Select single or group");
        selectAttributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAttributeButtonActionPerformed(evt);
            }
        });

        unselectAttributeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        unselectAttributeButton.setText("<");
        unselectAttributeButton.setToolTipText("Unselect single or group");
        unselectAttributeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unselectAttributeButtonActionPerformed(evt);
            }
        });

        unselectedAttributeCountLabel.setText("Count:");

        selectedAttributeCountLabel.setText("Count:");

        javax.swing.GroupLayout selectAttributePanelLayout = new javax.swing.GroupLayout(selectAttributePanel);
        selectAttributePanel.setLayout(selectAttributePanelLayout);
        selectAttributePanelLayout.setHorizontalGroup(
            selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectAttributePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectAttributePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(unselectAllAttributesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(unselectAttributeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectAttributeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectAllAttributesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(unselectedAttributeCountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectAttributePanelLayout.createSequentialGroup()
                        .addComponent(selectedAttributeCountLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        selectAttributePanelLayout.setVerticalGroup(
            selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(selectAttributePanelLayout.createSequentialGroup()
                .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(selectAttributePanelLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(selectAllAttributesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectAttributeButton)
                        .addGap(37, 37, 37)
                        .addComponent(unselectAttributeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unselectAllAttributesButton))
                    .addGroup(selectAttributePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectedAttributeCountLabel)
                            .addComponent(unselectedAttributeCountLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(selectAttributePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );

        outputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        outputTextArea.setEditable(false);
        outputTextArea.setBackground(new java.awt.Color(0, 0, 0));
        outputTextArea.setColumns(20);
        outputTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        outputTextArea.setForeground(new java.awt.Color(255, 255, 255));
        outputTextArea.setRows(5);
        outputTextArea.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(outputTextArea);

        javax.swing.GroupLayout outputPanelLayout = new javax.swing.GroupLayout(outputPanel);
        outputPanel.setLayout(outputPanelLayout);
        outputPanelLayout.setHorizontalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        outputPanelLayout.setVerticalGroup(
            outputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, outputPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout preprocessTabPanelLayout = new javax.swing.GroupLayout(preprocessTabPanel);
        preprocessTabPanel.setLayout(preprocessTabPanelLayout);
        preprocessTabPanelLayout.setHorizontalGroup(
            preprocessTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preprocessTabPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(preprocessTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectDataDirectoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectAttributePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        preprocessTabPanelLayout.setVerticalGroup(
            preprocessTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(preprocessTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(selectDataDirectoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectAttributePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Preprocess", preprocessTabPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Classify"));

        arffFilePathLabel.setText("Arff File Path:");

        browseArffFileButton.setText("Browse");
        browseArffFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseArffFileButtonActionPerformed(evt);
            }
        });

        trainPercentageLabel.setText("Train %:");

        classifyButton.setText("Classify");
        classifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classifyButtonActionPerformed(evt);
            }
        });

        classifierLabel.setText("Classifier:");

        classifierComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Naive Bayes", "Naive Bayes Multinomial", "Multilayer Perceptron", "SMO" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(trainPercentageLabel)
                    .addComponent(arffFilePathLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(arffFileReadPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseArffFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(trainPercentageForEvaluationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(classifierLabel)
                        .addGap(12, 12, 12)
                        .addComponent(classifierComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classifyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arffFilePathLabel)
                    .addComponent(arffFileReadPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseArffFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trainPercentageLabel)
                    .addComponent(trainPercentageForEvaluationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classifyButton)
                    .addComponent(classifierLabel)
                    .addComponent(classifierComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        evaluationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Confusion Matrix"));

        evaluationTextArea.setEditable(false);
        evaluationTextArea.setColumns(20);
        evaluationTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        evaluationTextArea.setRows(5);
        jScrollPane4.setViewportView(evaluationTextArea);

        javax.swing.GroupLayout evaluationPanelLayout = new javax.swing.GroupLayout(evaluationPanel);
        evaluationPanel.setLayout(evaluationPanelLayout);
        evaluationPanelLayout.setHorizontalGroup(
            evaluationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(evaluationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        evaluationPanelLayout.setVerticalGroup(
            evaluationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, evaluationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout experimentTabPanelLayout = new javax.swing.GroupLayout(experimentTabPanel);
        experimentTabPanel.setLayout(experimentTabPanelLayout);
        experimentTabPanelLayout.setHorizontalGroup(
            experimentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, experimentTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(experimentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(evaluationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        experimentTabPanelLayout.setVerticalGroup(
            experimentTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(experimentTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(evaluationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Experiment", experimentTabPanel);

        aboutTextArea.setEditable(false);
        aboutTextArea.setColumns(20);
        aboutTextArea.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        aboutTextArea.setRows(5);
        aboutTextArea.setText("Prizma version 1.0\n\nA Turkish Text Classification Environment for Weka\n\nPrizma is developed by Harun Reşit Zafer with Fatih Universtiy\nNatural Language Processing Group.\n\nhrzafer@fatih.edu.tr\nwww.hrzafer.com\nhttp://nlp.ceng.fatih.edu.tr/blog/\n\n\n\n");
        jScrollPane5.setViewportView(aboutTextArea);

        javax.swing.GroupLayout aboutTabPanelLayout = new javax.swing.GroupLayout(aboutTabPanel);
        aboutTabPanel.setLayout(aboutTabPanelLayout);
        aboutTabPanelLayout.setHorizontalGroup(
            aboutTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
        );
        aboutTabPanelLayout.setVerticalGroup(
            aboutTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("About", aboutTabPanel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 619, Short.MAX_VALUE)
        );

        mainTabbedPane.addTab("TF-IDF", jPanel2);

        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseDatasetDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseDatasetDirectoryButtonActionPerformed
        try {
            browse();
        } catch (NullPointerException e) {
            GUI.messageBox("Dataset directory not found!", "Error!");
        }
    }//GEN-LAST:event_browseDatasetDirectoryButtonActionPerformed

    private void browse() {
        JFileChooser chooser = getNewFileChooser(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooser.showDialog(browseDatasetDirectoryButton, "Select Dataset Directory");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            datasetDirectoryTextField.setText(chooser.getSelectedFile().getAbsolutePath());
            updateArffFileNameTextFieldByDatasetDirectoryName();
        }
    }

    private JFileChooser getNewFileChooser(int selectionMode) {
        JFileChooser chooser = new JFileChooser();
        String datasetDir = Config.get("lastDatasetDir");
        chooser.setCurrentDirectory(new java.io.File(datasetDir));
        chooser.setFileSelectionMode(selectionMode);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    private void createArffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createArffButtonActionPerformed
        create();
    }//GEN-LAST:event_createArffButtonActionPerformed

    private void create() {
        if (IsUserInputValid()) {
            outputTextArea.setText("ARFF File is being created. Please wait...\n\n");
            SwingUtilities.invokeLater(new CreateArffFileThread());
        }
    }

    private class CreateArffFileThread implements Runnable, MessageListener {

        @Override
        public void run() {
            Timer timer = new Timer();
            try {
                timer.start();
                String arffFilename = getArffFileWritePath();
                ArffProperties properties = getArffProperties();
                ArffCreator.create(properties, arffFilename + ".arff");
                //ArffCreator.createForEachKlass(properties, arffFilename + ".arff");
                timer.stop();
                outputTextArea.append("ARFF File created successfully...\n\nTime: " + timer.getElapsedSeconds() + " seconds");
            } catch (RuntimeException e) {
                timer.stop();
                GUI.messageBox("Dataset directory not found!", "Error!");
                outputTextArea.append(e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void messageSent(String message) {
            outputTextArea.append(message + "\n");
        }
    }

    private void selectAttributeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAttributeButtonActionPerformed
        selectOneOrMoreAttributes();
    }//GEN-LAST:event_selectAttributeButtonActionPerformed

    private void selectOneOrMoreAttributes() {
        int[] indices = unselectedAttributeList.getSelectedIndices();
        moveElements(_unselectedAttributeListModel, _selectedAttributeListModel, indices);
        updateAttributeCountLabels();
    }

    private void moveElements(DefaultListModel from, DefaultListModel to, int[] indices) {
        for (int i = indices.length - 1; i >= 0; i--) {
            moveElement(from, to, indices[i]);
        }
        sort(to);
    }

    private void sort(DefaultListModel listModel) {
        List<Feature> features = getListModelElementsAsAttributes(listModel);
        Collections.sort(features);
        listModel.clear();
        addAttributesAsElementsToListModel(listModel, features);
    }

    private List<Feature> getListModelElementsAsAttributes(DefaultListModel listModel) {
        List<Feature> features = new ArrayList<>();
        for (Object object : listModel.toArray()) {
            features.add((Feature) object);
        }
        return features;
    }

    private void moveElement(DefaultListModel from, DefaultListModel to, int index) {
        Object element = from.getElementAt(index);
        from.remove(index);
        to.addElement(element);
    }

    private void selectAllAttributesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllAttributesButtonActionPerformed
        selectAllAttributes();
    }//GEN-LAST:event_selectAllAttributesButtonActionPerformed

    private void selectAllAttributes() {
        int[] indices = getAllIndices(_unselectedAttributeListModel);
        moveElements(_unselectedAttributeListModel, _selectedAttributeListModel, indices);
        updateAttributeCountLabels();
    }

    private int[] getAllIndices(DefaultListModel model) {
        int size = model.getSize();
        int[] indices = new int[size];
        for (int i = 0; i < size; i++) {
            indices[i] = i;
        }
        return indices;
    }

    private void unselectAllAttributesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unselectAllAttributesButtonActionPerformed
        unselectAllAttributes();
    }//GEN-LAST:event_unselectAllAttributesButtonActionPerformed

    private void unselectAllAttributes() {
        int[] indices = getAllIndices(_selectedAttributeListModel);
        moveElements(_selectedAttributeListModel, _unselectedAttributeListModel, indices);
        updateAttributeCountLabels();
    }

    private void unselectAttributeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unselectAttributeButtonActionPerformed
        unselectOneOrMoreAttributes();
    }//GEN-LAST:event_unselectAttributeButtonActionPerformed

    private void unselectOneOrMoreAttributes() {
        int[] indices = selectedAttributeList.getSelectedIndices();
        moveElements(_selectedAttributeListModel, _unselectedAttributeListModel, indices);
        updateAttributeCountLabels();
    }

    private void datasetDirectoryTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_datasetDirectoryTextFieldKeyReleased
        updateArffFileNameTextFieldByDatasetDirectoryName();
    }//GEN-LAST:event_datasetDirectoryTextFieldKeyReleased

    private void updateArffFileNameTextFieldByDatasetDirectoryName() {
        String fileName = getArffFileNameFromDatasetDirectoryPath();
        //String str = Config.get("lastExperimentFile");
        //str = str.substring(str.lastIndexOf("/") + 1, str.indexOf("."));
        //arffFileWritePathTextField.setText("arff/" + str + "_" + fileName);
        arffFileWritePathTextField.setText("arff/" + fileName);
    }

    private String getArffFileNameFromDatasetDirectoryPath() {
        String path = getDatasetDirectoryPath();
        int startIndexOfName = path.lastIndexOf("/") + 1;
        return path.substring(startIndexOfName);
    }

    private int getStartIndexOfSlashOrUnderscore(String str) {
        int lastIndexOfSlash = str.lastIndexOf('_');
        int lastIndexOfUnderscore = str.lastIndexOf('/');
        if (lastIndexOfSlash > lastIndexOfUnderscore) {
            return lastIndexOfSlash;
        }
        return lastIndexOfUnderscore;
    }

    private void mainTabbedPaneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mainTabbedPaneFocusGained
        updateExperimentTabFields();
    }//GEN-LAST:event_mainTabbedPaneFocusGained

    private void updateExperimentTabFields() {
        updateExperimentTabTrainPercentageTextField();
        updateExperimentTabArffFileNameTextField();
    }

    private void updateExperimentTabTrainPercentageTextField() {
        String percentage = trainPercentageForCreationTextField.getText();
        trainPercentageForEvaluationTextField.setText(percentage);
    }

    private void updateExperimentTabArffFileNameTextField() {
        String name = arffFileWritePathTextField.getText() + ".arff";
        arffFileReadPathTextField.setText(name);
    }

    private void classifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classifyButtonActionPerformed
        classify();
    }//GEN-LAST:event_classifyButtonActionPerformed

    private void classify() {
        evaluationTextArea.setText("Classifying... Please Wait...");
        SwingUtilities.invokeLater(new ClassifyThread());
    }

    private class ClassifyThread implements Runnable {

        @Override
        public void run() {
            try {
                String path = getArffFileReadPath();
                double percentage = getTrainPercentageForEvaluation();
                String classifierName = getClassifierName();
                String evaluation = WekaHelper.evaluate(path, percentage, classifierName);
                evaluationTextArea.setText(evaluation);
            } catch (RuntimeException e) {
                GUI.messageBox("ARFF file not found!", "Error!");
                evaluationTextArea.setText(e.getMessage());
            }
        }
    }

    private void browseArffFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseArffFileButtonActionPerformed
        browseArff();
    }//GEN-LAST:event_browseArffFileButtonActionPerformed

    private String getCurrentDatasetDir(){
        String currentDatasetDir =datasetDirectoryTextField.getText();
        int index = currentDatasetDir.indexOf("\\");
        if (index < 0){
            return currentDatasetDir;
        }
        return currentDatasetDir.substring(0, index );
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    	String datasetPath = datasetDirectoryTextField.getText();
        String lastDatasetDir;
        String lastDataset;
        int index = datasetPath.lastIndexOf("\\");
        if (index > -1){
          lastDatasetDir = datasetPath.substring(0, index);
          lastDataset = datasetPath.substring(index+1);
        }
        else{
            lastDatasetDir = ".";
            lastDataset = datasetPath;
        }
        Config.set("lastDatasetDir", lastDatasetDir);
        Config.set("lastDataset", lastDataset);
        Config.save();
    }//GEN-LAST:event_formWindowClosing

    private void browseArff() {
        JFileChooser chooser = getNewFileChooser(JFileChooser.FILES_ONLY);
        int returnVal = chooser.showDialog(browseArffFileButton, "Select Arff File");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            arffFileReadPathTextField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutTabPanel;
    private javax.swing.JTextArea aboutTextArea;
    private javax.swing.JLabel arffFilePathLabel;
    private javax.swing.JTextField arffFileReadPathTextField;
    private javax.swing.JTextField arffFileWritePathTextField;
    private javax.swing.JButton browseArffFileButton;
    private javax.swing.JButton browseDatasetDirectoryButton;
    private javax.swing.JComboBox classifierComboBox;
    private javax.swing.JLabel classifierLabel;
    private javax.swing.JButton classifyButton;
    private javax.swing.JButton createArffButton;
    private javax.swing.JTextField datasetDirectoryTextField;
    private javax.swing.JPanel evaluationPanel;
    private javax.swing.JTextArea evaluationTextArea;
    private javax.swing.JPanel experimentTabPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JPanel preprocessTabPanel;
    private javax.swing.JCheckBox randomizeCheckBox;
    private javax.swing.JButton selectAllAttributesButton;
    private javax.swing.JButton selectAttributeButton;
    private javax.swing.JPanel selectAttributePanel;
    private javax.swing.JPanel selectDataDirectoryPanel;
    private javax.swing.JLabel selectedAttributeCountLabel;
    private javax.swing.JList selectedAttributeList;
    private javax.swing.JTextField trainPercentageForCreationTextField;
    private javax.swing.JTextField trainPercentageForEvaluationTextField;
    private javax.swing.JLabel trainPercentageLabel;
    private javax.swing.JCheckBox unicodeCheckBox;
    private javax.swing.JButton unselectAllAttributesButton;
    private javax.swing.JButton unselectAttributeButton;
    private javax.swing.JLabel unselectedAttributeCountLabel;
    private javax.swing.JList unselectedAttributeList;
    // End of variables declaration//GEN-END:variables
}
