package app;

/**

 Created by Milene Pereira Guimarães de Jezuz on 03/08/2016.

 */

import app.service.ImportDataBiomicDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@PropertySource("application.properties")
public class ImportFastaApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ImportFastaApplication.class);
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

/* COMENTADO PARA CRIAR O JAR
    public static void main(String args[]) {

        SpringApplication.run(ImportFastaApplication.class, args);
    }
*/

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
                System.out.println("\nimportFasta-0.1.0: A process to import fasta data\n");
                System.out.println("Example: java -jar importFasta-0.1.0.jar -f Angiostrongylys_proteome_Uniref100_annotation.fasta -p C:\\files\\fastas\n");
                System.out.println("Arguments:\n");
                System.out.println("-f: File fasta to import");
                System.out.println("-p: Path of fasta files");
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

        System.out.println("Validating importFasta-0.1.0 arguments...");

        if (argumentsValidation(args)) {

            System.out.println("Starting importFasta-0.1.0 process...");
            System.out.println("path: " + path);
            System.out.println("file: " + file);

            ImportDataBiomicDataService service = new ImportDataBiomicDataService();

            service.importFastaData(path, file);

            System.out.println("importFasta-0.1.0 process finished. See the file '" + fileName + "' created.");
        }

    }

}