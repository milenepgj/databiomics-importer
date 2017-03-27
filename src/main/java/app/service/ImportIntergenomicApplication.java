package app.service;

/**

 Created by Milene Pereira Guimarães de Jezuz on 03/08/2016.

 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@PropertySource("application.properties")
public class ImportIntergenomicApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ImportIntergenomicApplication.class);
    private List<String> resultContaisInBoth = new ArrayList<String>();
    private List<String> resultOnlyInC = new ArrayList<String>();
    private List<String> resultOnlyInF = new ArrayList<String>();
    private String file = null;
    private String path = null;
    private String outputPath = null;
    private List<String> listF = null;
    private List<String> listC = null;
    private String fileName;
    private boolean help = false;

    public static void main(String args[]) {

        SpringApplication.run(ImportIntergenomicApplication.class, args);
    }


    private boolean argumentsValidation(String... args){

        if (args == null || args.length == 0){
            System.out.println("Please inform the arguments. For more explanation, put -help argument.");
        }else {
            for (int l = 0; l < args.length; l++) {
                String argument = args[l];

                switch (argument) {
                    case "-f":
                        file = args[l + 1];
                        break;
                    case "-p":
                        path = args[l + 1];
                        break;
                    case "-help":
                        help = true;
                        break;
                }
            }

            if (help) {
                System.out.println("\nimportIntergenomicApplication-0.1.0: A process to import intergenomic data\n");
                System.out.println("Example: java -jar importIntergenomicApplication-0.1.0.jar -f Lista_drug_targets_AngioDB.xlsx -p C:\\files\\xls\n");
                System.out.println("Arguments:\n");
                System.out.println("-f: File to import");
                System.out.println("-p: Path of files");
                System.out.println("-help: Show the arguments");
            } else if (path == null) {
                System.out.println("Please inform the path (put the -p argument).");
            } else if (file == null) {
                System.out.println("Please inform the file (put the -f argument).");
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Validating importIntergenomicApplication-0.1.0 arguments...");

        if (argumentsValidation(args)) {

            System.out.println("Starting importIntergenomicApplication-0.1.0 process...");
            System.out.println("path: " + path);
            System.out.println("file: " + file);

            ImportDataBiomicDataService service = new ImportDataBiomicDataService();

            service.importIntergenomicData(path, file);

            System.out.println("importIntergenomicApplication-0.1.0 process finished. See the file '" + fileName + "' created.");
        }

    }

}