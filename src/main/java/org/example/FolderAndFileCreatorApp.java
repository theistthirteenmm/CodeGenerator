package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

public class FolderAndFileCreatorApp extends JFrame {

    private JTextField rootFolderNameField;
    private JTextField rootFolderNameFieldF;

    private JTextField rootFolderPathField;
    private JTextField rootFolderPathFieldClient;

    String filePath = "path/to/configCodeGenerator.properties";
    String filePathpermissions = "path/to/permissions-config.txt";


    String filePathPermissions ;





    private static void createConfigFileIfNotExists(String filePath) {
        File configFile = new File(filePath);

        // اگر فایل وجود نداشته باشد، آن را ایجاد کنید
        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    System.out.println("فایل config.properties با موفقیت ایجاد شد.");
//                    // اضافه کردن محتوای اولیه به فایل
//                    writeDefaultContentToFile(configFile);
                } else {
                    System.out.println("خطا در ایجاد فایل config.properties!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("خطا در ایجاد فایل config.properties: " + e.getMessage());
            }
        }
    }


    private static void createConfigFileIfpermissionsconfig(String filePath,String rootName,String rootnameF) {
        File configFile = new File(filePath);

        // اگر فایل وجود نداشته باشد، آن را ایجاد کنید

            try {
                if (configFile.createNewFile()) {
                    System.out.println("فایل config.properties با موفقیت ایجاد شد.");
                    // اضافه کردن محتوای اولیه به فایل
                    writeDefaultContentToFile(configFile,rootName,rootnameF);
                } else {
                    System.out.println("خطا در ایجاد فایل config.properties!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("خطا در ایجاد فایل config.properties: " + e.getMessage());
            }




    }

    private static void writeDefaultContentToFile(File configFile,String rootName,String rootNamef) {
        // محتوای پیشفرض برای فایل config.properties
        String defaultContent =
                   rootNamef+",/"+rootName+"POST"+
                   rootNamef+",/"+rootName+"GET"+
                   rootNamef+",/"+rootName+"PUT"+
                   rootNamef+",/"+rootName+"DELETE"
                ;

        // نوشتن محتوای پیشفرض به فایل
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(defaultContent);
            System.out.println("محتوای پیشفرض به فایل config.properties اضافه شد.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("خطا در نوشتن محتوای پیشفرض به فایل config.properties: " + e.getMessage());
        }
    }


    public FolderAndFileCreatorApp() {
        // تنظیمات پنجره
        setTitle("config Code Generator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // ایجاد المان‌های ورودی و برچسب
        JLabel rootFolderNameLabel = new JLabel("نام ماژول:");
        rootFolderNameField = new JTextField();


        JLabel rootFolderNameLabelFarsi = new JLabel("نام فارسی ماژول:");
        rootFolderNameFieldF = new JTextField();


        JLabel rootFolderPathLabel = new JLabel("مسیر اصلی سرور:");
        rootFolderPathField = new JTextField();

        JLabel rootFolderPathClient = new JLabel("مسیر اصلی کلاینت:");
        rootFolderPathFieldClient = new JTextField();

        // خواندن مسیر اصلی از فایل کانفیگ
        try {
            Properties properties = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("configCodeGenerator.properties");
            //if (input==null)createConfigFileIfNotExists(filePath);
            properties.load(input);
            String defaultServer = properties.getProperty("rootPathServer");
            String defaultClient = properties.getProperty("rootPathClient");
            filePathPermissions = properties.getProperty("rootPathPermissions");
            rootFolderPathField.setText(defaultServer);
            rootFolderPathFieldClient.setText(defaultClient);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "خطا در خواندن فایل کانفیگ!", "خطا", JOptionPane.ERROR_MESSAGE);
        }




//        JButton browseButton = new JButton("انتخاب مسیر");
//
//
//        browseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//                int result = fileChooser.showOpenDialog(FolderAndFileCreatorApp1.this);
//
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    String selectedPath = fileChooser.getSelectedFile().getPath();
//                    rootFolderPathField.setText(selectedPath);
//                }
//            }
//        });

        JButton createFoldersAndFilesButton = new JButton("ساخت پوشه‌ها و فایل‌ها");
        createFoldersAndFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rootFolderName = rootFolderNameField.getText();
                String rootFolderPathClient = rootFolderPathFieldClient.getText();
                String rootFolderPath = rootFolderPathField.getText();
                String rootFolderNameFarsi = rootFolderNameFieldF.getText();



                if (!rootFolderName.isEmpty() && !rootFolderPath.isEmpty()) {
                    File rootFolder = new File(rootFolderPath, rootFolderName);
                    if (!rootFolder.exists()) {
                        rootFolder.mkdir();
                    }

                    String[] subfolderNames = {"controller", "dao","dto","mapper","service"};
                    String fileNamesController = rootFolderName+"Controller.java";
                    String[] fileNamesDaos = {rootFolderName+"Dao.java",rootFolderName+"Repository.java"};
                    String[] fileNamesservices = {rootFolderName+"ServiceImpl.java",rootFolderName+"Service.java"};
                    String[] fileNamemains = {rootFolderName+"Entity.java",rootFolderName+"FileMessages.properties"};



                        for (String fileNamemain : fileNamemains) {
                            String capitalizedString = capitalizeFirstLetter(fileNamemain);
                            File file = new File(rootFolder, capitalizedString);
                            try {
                                if (file.createNewFile()) {

                                    if (fileNamemain.equals(rootFolderName+"Entity.java")){
                                        writeTextToFileMain(file);


                                        File file1 = new File(filePathPermissions,"permissions-config.txt");


                                        writeTextTopermissionsconfig(file1,rootFolderName,rootFolderNameFarsi);

                                      // createConfigFileIfpermissionsconfig(filePathPermissions,rootFolderName,rootFolderNameFarsi);



                                    }else {
                                        writeTextToFileproperties(file,rootFolderNameFarsi);
                                    }

                                    System.out.println("ok");

                                } else {
                                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, fileNamemain + " در " + rootFolder + " از قبل وجود دارد!", "اخطار", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "خطا در ایجاد فایل!", "خطا", JOptionPane.ERROR_MESSAGE);
                            }
                        }






                    // ایجاد زیرپوشه‌ها
                    for (String subfolderName : subfolderNames) {

                        File subfolder = new File(rootFolder, subfolderName);
                        if (!subfolder.exists()) {
                            subfolder.mkdir();
                        }

                        if (subfolderName.equals("controller")) {
                            String capitalizedString = capitalizeFirstLetter(fileNamesController);
                                File file = new File(subfolder, capitalizedString);
                                try {
                                    if (file.createNewFile()) {
                                        writeTextToFileController(file);
                                        System.out.println("ok");
                                    } else {
                                        JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, fileNamesController + " در " + subfolderName + " از قبل وجود دارد!", "اخطار", JOptionPane.WARNING_MESSAGE);
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "خطا در ایجاد فایل!", "خطا", JOptionPane.ERROR_MESSAGE);
                                }

                        }

                        if (subfolderName.equals("dao")) {
                            for (String fileNamesDao : fileNamesDaos) {
                                String capitalizedString = capitalizeFirstLetter(fileNamesDao);
                                File file = new File(subfolder, capitalizedString);
                                try {
                                    if (file.createNewFile()) {
                                        if (fileNamesDao.equals(rootFolderName+"Dao.java")){
                                            writeTextToFiledao(file);
                                        }else {
                                            writeTextToFilerpo(file);
                                        }


                                        System.out.println("ok");

                                    } else {
                                        JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, fileNamesDao + " در " + subfolderName + " از قبل وجود دارد!", "اخطار", JOptionPane.WARNING_MESSAGE);
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "خطا در ایجاد فایل!", "خطا", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }


                        if (subfolderName.equals("service")) {
                            for (String fileNamesservice : fileNamesservices) {
                                String capitalizedString = capitalizeFirstLetter(fileNamesservice);
                                File file = new File(subfolder, capitalizedString);
                                try {
                                    if (file.createNewFile()) {

                                        if (fileNamesservice.equals(rootFolderName+"ServiceImpl.java")){
                                            writeTextToFileservicimpl(file);
                                        }else {
                                            writeTextToFileservic(file);
                                        }


                                        System.out.println("ok");

                                    } else {
                                        JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, fileNamesservice + " در " + subfolderName + " از قبل وجود دارد!", "اخطار", JOptionPane.WARNING_MESSAGE);
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "خطا در ایجاد فایل!", "خطا", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }




                    }

                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "پوشه‌ها و فایل‌ها با موفقیت ایجاد شدند!");
                } else {
                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "لطفاً نام پوشه اصلی و مسیر را وارد کنید!", "اخطار", JOptionPane.WARNING_MESSAGE);
                }






                if (!rootFolderName.isEmpty() && !rootFolderPathClient.isEmpty()) {
                    File rootFolder = new File(rootFolderPathClient, rootFolderName);
                    if (!rootFolder.exists()) {
                        rootFolder.mkdir();
                    }

                    //String[] subfolderNames = {"controller", "dao","dto","mapper","service"};
//                    String fileNamescreateView = rootFolderName+"createView.java";
//                    String fileNamesdisplayView = rootFolderName+"createView.java";
//                    String fileNamesService = rootFolderName+"service.java";
//                    String[] fileNamesDaos = {rootFolderName+"Dao.java",rootFolderName+"Repository.java"};
//                    String[] fileNamesservices = {rootFolderName+"ServiceImpl.java",rootFolderName+"Service.java"};
                    String[] fileNamemains = {rootFolderName+"CreateView.js",rootFolderName+"DisplayView",rootFolderName+"Service.js"};



                    for (String fileNamemain : fileNamemains) {
                        String capitalizedString = capitalizeFirstLetter(fileNamemain);
                        File file = new File(rootFolder, capitalizedString);
                        try {
                            if (file.createNewFile()) {

                                if (fileNamemain.equals(rootFolderName+"CreateView.js")){
                                    writeTextToFilecreateView(file);
                                }else if (fileNamemain.equals(rootFolderName+"DisplayView.java")){
                                    writeTextToFiledisplayView(file);
                                }else {
                                    writeTextToFileservice(file);
                                }

                                System.out.println("ok");

                            } else {
                                JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, fileNamemain + " در " + rootFolder + " از قبل وجود دارد!", "اخطار", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "خطا در ایجاد فایل!", "خطا", JOptionPane.ERROR_MESSAGE);
                        }
                    }





//                    // ایجاد زیرپوشه‌ها
//                    for (String subfolderName : subfolderNames) {
//
//                        File subfolder = new File(rootFolder, subfolderName);
//                        if (!subfolder.exists()) {
//                            subfolder.mkdir();
//                        }
//
//
//
//                    }

                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "پوشه‌ها و فایل‌ها با موفقیت ایجاد شدند!");
                } else {
                    JOptionPane.showMessageDialog(FolderAndFileCreatorApp.this, "لطفاً نام پوشه اصلی و مسیر را وارد کنید!", "اخطار", JOptionPane.WARNING_MESSAGE);
                }







            }
        });

        // اضافه کردن المان‌ها به پنجره
        add(rootFolderNameLabel);
        add(rootFolderNameField);
        add(rootFolderNameLabelFarsi);
        add(rootFolderNameFieldF);
        add(rootFolderPathLabel);
        add(rootFolderPathField);

        add(rootFolderPathClient);
        add(rootFolderPathFieldClient);


        add(new JLabel()); // برای فضای خالی
        add(createFoldersAndFilesButton);

        // نمایش پنجره
        setVisible(true);
    }






    //client
    private void writeTextToFiledisplayView(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".js","");

            writer.write(

                   "const "+name+" = () => {\n" +
                           "   return (\n" +
                           "       <>\n" +
                           "\n" +
                           "       </>\n" +
                           "   )\n" +
                           "}\n" +
                           "\n" +
                           "export default "+name+";"


            );

        }
    }



    private void writeTextToFilecreateView(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".js","");

            writer.write(

                    "const "+name+" = () => {\n" +
                            "   return (\n" +
                            "       <>\n" +
                            "\n" +
                            "       </>\n" +
                            "   )\n" +
                            "}\n" +
                            "\n" +
                            "export default "+name+";"


            );

        }
    }

    private void writeTextToFileservice(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(

                    ""


            );

        }
    }



    // تابع برای تغییر حرف اول به حروف بزرگ
    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }



    private static String capitalizeFirstLetterbacke(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }


    // تابع برای نوشتن متن به یک فایل
    private void writeTextTopermissionsconfig(File file, String rootName, String rootnameF) throws IOException {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(
                        rootnameF + ", /" + rootName + ", POST\n" +
                            rootnameF + ", /" + rootName + ", GET\n" +
                            rootnameF + ", /" + rootName + ", PUT\n" +
                            rootnameF + ", /" + rootName + ", DELETE\n"
            );
        }
    }

    private void writeTextToFileMain(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {



            String name=  file.getName().replace(".java","");
            String name1=  name.replace("Entity","");

            String transformedString = name1.replaceAll("(?<=.)(?=\\p{Lu})", "_");
            String transformedString1= "TBL_"+transformedString.toUpperCase();



            writer.write(

                    "@Data\n" +
                            "@NoArgsConstructor\n" +
                            "@Entity\n" +
                            "@Table(name = \""+transformedString1+"\")\n" +
                            "\n" +
                            "public class "+name+" extends BaseEntity {\n" +
                            "\n" +
                            "}"

            );

        }
    }





    private void writeTextToFileproperties(File file,String f) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace("FileMessages.properties","");
            String capitalizedString = capitalizeFirstLetterbacke(name);
            writer.write(

                    capitalizedString+"="+f


            );

        }
    }


    private void writeTextToFileController(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".java","");
            String name1 = name.replaceAll("(?<=.)(?=\\p{Lu})", "-");
            String name2=name1.toLowerCase();
            writer.write(

                    "@RequiredArgsConstructor\n" +
                            "@RestController\n" +
                            "@RequestMapping(\"/"+name2+"\")\n" +
                            "public class "+name+" {\n" +
                            "\n" +
                            "   \n" +
                            "}"


            );

        }
    }



    private void writeTextToFiledao(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".java","");




            writer.write(

                    "@RequiredArgsConstructor\n" +
                            "@Repository\n" +
                            "public class "+name+" extends BaseDao {\n" +
                            "\n" +
                            "    \n" +
                            "\n" +
                            "}"


            );

        }
    }


    private void writeTextToFilerpo(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".java","");
            String name1=  name.replace("Repository","");




            writer.write(

                    "@Repository\n" +
                            "public interface "+name+" extends JpaRepository<"+name1+"Entity, String> {\n" +
                            "\n" +
                            "}"


            );

        }
    }



    private void writeTextToFileservicimpl(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".java","");
            String name1=  name.replace("Impl","");

            String name4=  name.replace("ServiceImpl","");


            String capitalizedString = capitalizeFirstLetterbacke(name);



            writer.write(

                    "@RequiredArgsConstructor\n" +
                            "@Service\n" +
                            "public class "+name+" implements "+name1+" {\n" +
                            "\n" +
                            "    private final "+name4+"Repository "+capitalizedString+"Repository;\n" +
                            "    private final "+name4+"Dao "+capitalizedString+"Dao;\n" +
                            "\n" +
                            " \n" +
                            "\n" +
                            "\n" +
                            "}"


            );

        }
    }



    private void writeTextToFileservic(File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            String name=  file.getName().replace(".java","");



            writer.write(

                  "public interface "+name+" {\n" +
                          "\n" +
                          "    \n" +
                          "}"


            );

        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FolderAndFileCreatorApp();
            }
        });
    }
}
