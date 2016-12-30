package app;

/**

 Created by Milene Pereira Guimarães de Jezuz on 03/08/2016.

 */

import app.service.ImportDataBiomicDataService;
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
public class ImportBlastDataApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ImportBlastDataApplication.class);
    private List<String> resultContaisInBoth = new ArrayList<String>();
    private List<String> resultOnlyInC = new ArrayList<String>();
    private List<String> resultOnlyInF = new ArrayList<String>();
    private String type = null;
    private String file = null;
    private String path = null;
    private String outputPath = null;
    private List<String> listF = null;
    private List<String> listC = null;
    private String fileName;
    private boolean help = false;

    public static void main(String args[]) {

        SpringApplication.run(ImportBlastDataApplication.class, args);
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
                    case "-t":
                        type = args[l + 1];
                        break;
                    case "-help":
                        help = true;
                        break;
                }
            }

            if (help) {
                System.out.println("\nimportBlastData-0.1.0: A process to import blast data\n");
                System.out.println("Example: java -jar importBlastData-0.1.0.jar -t NCBI -f ncbi.csv -p C:\\files\\blastData\n");
                System.out.println("Arguments:\n");
                System.out.println("-f: CSV file to import");
                System.out.println("-p: Path of CSV files");
                System.out.println("-t: Data type (e.g NCBI)");
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

        System.out.println("Validating importBlastData-0.1.0 arguments...");

        if (argumentsValidation(args)) {

            System.out.println("Starting importBlastData-0.1.0 process...");
            System.out.println("type: " + type);
            System.out.println("path: " + path);
            System.out.println("file: " + file);

            ImportDataBiomicDataService service = new ImportDataBiomicDataService();

            service.importSequencesData(type, path, file);

            System.out.println("importBlastData-0.1.0 process finished. See the file '" + fileName + "' created.");
        }

    }

}