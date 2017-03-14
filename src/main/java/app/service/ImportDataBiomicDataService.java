package app.service;


import app.dto.*;
import app.hibernate.HibernateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ImportDataBiomicDataService {

    /* COMENTADO PARA CRIAR O JAR
    public static void main(String[] args) {

        ImportDataBiomicDataService obj = new ImportDataBiomicDataService();
        obj.importSequencesData("NCBI", "/home/milene.guimaraes/Dropbox/Planilhas AngiostrongylusDB", "Tabela_Anotacao_Angiostrongylus_NCBI.txt");
        //obj.importSequencesData("NEMATODA");
        //obj.importSequencesData("UNIREF100");
        //obj.importSequencesData("TREMBL");

    }*/

    private Sequence getSequenceData(String[] line){

        Sequence sequence = new Sequence();
        sequence.setSeqName(line[0]);
        sequence.setDescription(line[1]);
        sequence.setLength(Integer.parseInt(line[2]));
        sequence.setNumberHits(Integer.parseInt(line[3]));
        sequence.seteValue(line[4]);
        sequence.setSimMean(Double.parseDouble(line[5].replace("%","").replace("-","0").replace(",",".")));
        sequence.setGo(line[6]);
        sequence.setGoNames(line[7]);
        sequence.setEnzimeCodes(line[8]);
        sequence.setInterproIds(line[9]);

        return sequence;

    }

    private UnirefNematoda getUnirefNematodaData(String[] line){

        UnirefNematoda unirefNematoda = new UnirefNematoda();

        String[] seqOrfPtns = line[0].split("\\|");

        unirefNematoda.setSeqName(seqOrfPtns[0]);
        unirefNematoda.setOrfsPtns(seqOrfPtns[1]);

        unirefNematoda.setDescription(line[1]);
        unirefNematoda.setLength(Integer.parseInt(line[2]));
        unirefNematoda.setNumberHits(Integer.parseInt(line[3]));
        unirefNematoda.seteValue(line[4]);
        unirefNematoda.setSimMean(Double.parseDouble(line[5].replace("%","").replace("-","0").replace(",",".")));

        return unirefNematoda;

    }

    private Uniref100 getUniref100(String[] line){

        Uniref100 uniref100 = new Uniref100();

        String[] seqOrfPtns = line[0].split("\\|");

        uniref100.setSeqName(seqOrfPtns[0]);
        uniref100.setOrfsPtns(seqOrfPtns[1]);

        uniref100.setDescription(line[1]);
        uniref100.setLength(Integer.parseInt(line[2]));
        uniref100.setNumberHits(Integer.parseInt(line[3]));
        uniref100.seteValue(line[4]);
        uniref100.setSimMean(Double.parseDouble(line[5].replace("%","").replace("-","0").replace(",",".")));

        return uniref100;

    }

    private Trembl getTremblData(String[] line){

        Trembl trembl = new Trembl();
        String[] seqOrfPtns = line[0].split("\\|");

        trembl.setSeqName(seqOrfPtns[0]);
        trembl.setOrfsPtns(seqOrfPtns[1]);

        trembl.setDescription(line[1]);
        trembl.setLength(Integer.parseInt(line[2]));
        trembl.setNumberHits(Integer.parseInt(line[3]));
        trembl.seteValue(line[4]);
        trembl.setSimMean(Double.parseDouble(line[5].replace("%","").replace("-","0").replace(",",".")));
        trembl.setInterproIds(line[6]);

        return trembl;

    }

    private Fasta getFastaData(String seqName, String orfsPtns, String fastaDescription, String fastaContent){

        Fasta fasta = new Fasta();
        fasta.setSeqName(seqName);
        fasta.setOrfsPtns(orfsPtns);
        fasta.setFastaDescription(fastaDescription);
        fasta.setFastaContent(fastaContent);

        return fasta;

    }

    private MRnaFasta getMRnaFastaData(String seqName, String fastaDescription, String fastaContent){

        MRnaFasta fasta = new MRnaFasta();
        fasta.setSeqName(seqName);
        fasta.setFastaDescription(fastaDescription);
        fasta.setFastaContent(fastaContent);

        return fasta;

    }

    private Intragenomic getIntragenomicData(String[] line){

        Intragenomic intragenomic = new Intragenomic();
        intragenomic.setEc_number(line[0].replaceAll("'",""));

        String[] seqOrf = line[1].split("\\|");

        intragenomic.setSeqName(seqOrf[0]);
        intragenomic.setOrfsPtns(seqOrf[1]);

        intragenomic.setTpm(line[2].replaceAll("'",""));
        intragenomic.setFpkm(line[3].replaceAll("'",""));
        intragenomic.setEnzymeDescription(line[4].replaceAll("'",""));
        intragenomic.setUniref100(line[5].replaceAll("'",""));
        intragenomic.setLength(Double.parseDouble(line[6]==null?"0":line[6].replaceAll("'","")));
        intragenomic.setNumberHits(Double.parseDouble(line[7]==null?"0":line[7].replaceAll("'","")));
        intragenomic.seteValue(line[8].replaceAll("'",""));
        intragenomic.setSimMean(Double.parseDouble(line[9]==null?"0":line[9].replaceAll("%","").replaceAll("'","")));
        intragenomic.setFold(line[10].replaceAll("'",""));
        intragenomic.setSuperfamily(line[11].replaceAll("'",""));
        intragenomic.setLiterature_function(line[12].replaceAll("'",""));

        return intragenomic;

    }

    public void importSequencesData(String type, String path, String file) {

        String arquivoCSV = path + File.separator + file;

        if (arquivoCSV == ""){
            System.out.println("nothing to do");
        }else{

            BufferedReader br = null;
            String linha = "";
            String csvDivisor = "\\t";
            List<Sequence> sequences = new ArrayList<Sequence>();
            List<UnirefNematoda> unirefNematodaSequences = new ArrayList<UnirefNematoda>();
            List<Uniref100> uniref100Sequences = new ArrayList<Uniref100>();
            List<Trembl> tremblSequences = new ArrayList<Trembl>();
            boolean header = true;

            try {

                br = new BufferedReader(new FileReader(arquivoCSV));
                while ((linha = br.readLine()) != null) {
                    String[] line = linha.split(csvDivisor);
                    if (line[0].contains("SeqName")){
                        header = false;
                    }else{
                        switch (type){
                            case "NCBI":
                                sequences.add((Sequence)save(getSequenceData(line)));
                                break;
                            case "NEMATODA":
                                unirefNematodaSequences.add((UnirefNematoda) save(getUnirefNematodaData(line)));
                                break;
                            case "UNIREF100":
                                uniref100Sequences.add((Uniref100) save(getUniref100(line)));
                                break;
                            case "TREMBL":
                                tremblSequences.add((Trembl)save(getTremblData(line)));
                                break;
                        }

                    }
                }

                System.out.println("TOTAL ITEMS ON SEQUENCE TABLE:" + sequences.size());
                System.out.println("TOTAL ITEMS ON UNIREFNEMATODA TABLE:" + unirefNematodaSequences.size());
                System.out.println("TOTAL ITEMS ON UNIREF100 TABLE:" + uniref100Sequences.size());
                System.out.println("TOTAL ITEMS ON TREMBL TABLE:" + tremblSequences.size());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void importFastaData(String filesPath, String file) {

        if (filesPath == "" || file == ""){
            System.out.println("nothing to do");
        }else{

            BufferedReader br = null;
            String linha = "";
            String sequenceCharIdentity = ">";
            List<Fasta> fastas = new ArrayList<Fasta>();
            List<Fasta> fastasToSave = new ArrayList<Fasta>();
            String seqName = "";
            String orfsPtns = "";
            String fastaDescription = "";
            StringBuilder fastaContent = null;
            Fasta fasta = null;
            int qtdLinhas = 0;
            try {

                br = new BufferedReader(new FileReader(filesPath + File.separator + file));
                while ((linha = br.readLine()) != null) {

                    qtdLinhas++;

                    if (linha.contains(sequenceCharIdentity)){

                        if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                            //Salva o fasta (para nematoda, uniref e trembl)
                            fasta.setFastaContent(fastaContent.toString());
                            //fasta = (Fasta)save(fasta);
                            fastasToSave.add(fasta);
                            fastaContent = null;

                            //fastas = saveFastaData(fasta, fastaContent, fastas);
                        }

                        fasta = getFastaIdentity(linha);

                    }else{
                        if (fastaContent != null) {
                            fastaContent.append(System.getProperty("line.separator"));
                        }else{
                            fastaContent = new StringBuilder();
                        }

                        fastaContent.append(linha);
                    }

                }

                if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                    //Salva o último fasta
                    //fastas = saveFastaData(fasta, fastaContent, fastas);

                    fasta.setFastaContent(fastaContent.toString());
                    //fasta = (Fasta)save(fasta);
                    fastasToSave.add(fasta);
                }

                System.out.println("TOTAL FASTA ITEMS PARA SALVAR:" + fastasToSave.size());

                for (int i=0; i<fastasToSave.size(); i++) {
                    fasta = (Fasta)save(fastasToSave.get(i));
                    fastas = saveFastaData(fastasToSave.get(i), fastaContent.toString(), fastas);
                }

                System.out.println("TOTAL FASTA ITEMS SALVOS:" + fastas.size());

            } catch (FileNotFoundException e) {
                System.out.println ("linhas: " + qtdLinhas);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println ("linhas: " + qtdLinhas);
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        System.out.println ("linhas: " + qtdLinhas);
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private MRnaFasta saveMRnaFastaData(MRnaFasta fasta){

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Object returnObject = null;

        Sequence sequence = (Sequence) getItemByMRnaFasta(fasta);
        if (sequence != null) {
            sequence.setFastaContent(fasta.getFastaContent());
            sequence.setFastaDescription(fasta.getFastaDescription());
        }
        returnObject = save(sequence);

        session.close();

        return fasta;
    }

    private static Object getItemByMRnaFasta(MRnaFasta fasta) {

        //https://www.mkyong.com/hibernate/different-between-session-get-and-session-load/

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Object returnObject = null;
        String hql = "from table s where s.seqName = :seqName";

        hql = hql.replaceAll("table", "Sequence");

        returnObject = session.createQuery(hql)
                .setProperties(fasta).uniqueResult();

        session.close();

        return returnObject;

    }

    private List<MRnaFasta> saveMRnaFastaData(MRnaFasta mRnafasta, String fastaContent, List<MRnaFasta> fastas){

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Object returnObject = null;

        Sequence sequence = (Sequence) getItemByMRnaFasta(mRnafasta);
        if (sequence != null) {
            sequence.setFastaContent(mRnafasta.getFastaContent());
            sequence.setFastaDescription(mRnafasta.getFastaDescription());
        }
        returnObject = save(sequence);
        session.close();

        fastas.add((MRnaFasta)returnObject);

        return fastas;
    }

    private List<Fasta> saveFastaData(Fasta fasta, String fastaContent, List<Fasta> fastas){

        saveFastaDataByClassName("UNIREF100", fasta);
        //saveFastaDataByClassName("NEMATODA", fasta);
        //saveFastaDataByClassName("TREMBL", fasta);

        return fastas;
    }

    private Fasta getFastaIdentity (String linha){
        String[] sequenceIdentity = linha.split("\\|");
        String seqName = sequenceIdentity[0].replaceAll(">","");
        String orfsPtns = sequenceIdentity[1];
        String fastaDescription = sequenceIdentity[2];

        return getFastaData(seqName, orfsPtns, fastaDescription, "");
    }

    private MRnaFasta getMRnaFastaIdentity (String linha) {
        String fastaDescription = "";
        String[] sequenceIdentity = linha.split("\\|");
        String seqName = sequenceIdentity[0].replaceAll(">", "");

        if (sequenceIdentity.length > 1) fastaDescription = sequenceIdentity[1];

        return getMRnaFastaData(seqName, fastaDescription, "");
    }

    private static Object save(Object object) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        session.saveOrUpdate(object);

        session.getTransaction().commit();

        session.close();

        return object;
    }

    private static Object getItemByFasta(String className, Fasta fasta) {

        //https://www.mkyong.com/hibernate/different-between-session-get-and-session-load/

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Object returnObject = null;
        String hql = "from table s where s.seqName = :seqName and s.orfsPtns = :orfsPtns";

        switch (className){
            case "NEMATODA":
                hql = hql.replaceAll("table", "UnirefNematoda");

                returnObject = session.createQuery(hql)
                        .setProperties(fasta).uniqueResult();

            case "UNIREF100":
                hql = hql.replaceAll("table", "Uniref100");

                returnObject = session.createQuery(hql)
                        .setProperties(fasta).uniqueResult();

            case "TREMBL":
                hql = hql.replaceAll("table", "Trembl");

                returnObject = session.createQuery(hql)
                        .setProperties(fasta).uniqueResult();

        }
        session.close();
        return returnObject;

    }

    private static Object saveFastaDataByClassName(String className, Fasta fasta) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        Object returnObject = null;

        switch (className){
            case "NEMATODA":
                UnirefNematoda unirefNematoda = (UnirefNematoda) getItemByFasta("NEMATODA", fasta);
                if (unirefNematoda != null) {
                    unirefNematoda.setFastaContent(fasta.getFastaContent());
                    unirefNematoda.setFastaDescription(fasta.getFastaDescription());
                }
                returnObject = save(unirefNematoda);
            case "UNIREF100":
                Uniref100 uniref100 = (Uniref100) getItemByFasta("UNIREF100", fasta);
                if (uniref100 != null) {
                    uniref100.setFastaContent(fasta.getFastaContent());
                    uniref100.setFastaDescription(fasta.getFastaDescription());
                }
                returnObject = save(uniref100);
            case "TREMBL":
                Trembl trembl = (Trembl) getItemByFasta("TREMBL", fasta);
                if (trembl != null) {
                    trembl.setFastaContent(fasta.getFastaContent());
                    trembl.setFastaDescription(fasta.getFastaDescription());
                }
                returnObject = save(trembl);
        }
        session.close();

        return returnObject;

    }

    public void importMRnaFastaData(String filesPath, String file) {

        if (filesPath == "" || file == ""){
            System.out.println("nothing to do");
        }else{

            BufferedReader br = null;
            String linha = "";
            String sequenceCharIdentity = ">";
            List<MRnaFasta> fastas = new ArrayList<MRnaFasta>();
            List<MRnaFasta> fastasToSave = new ArrayList<MRnaFasta>();
            String seqName = "";
            String orfsPtns = "";
            String fastaDescription = "";
            StringBuilder fastaContent = null;
            MRnaFasta fasta = null;
            int qtdLinhas = 0;
            try {

                br = new BufferedReader(new FileReader(filesPath + File.separator + file));
                while ((linha = br.readLine()) != null) {

                    qtdLinhas++;

                    if (linha.contains(sequenceCharIdentity)){

                        if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                            //Salva o fasta (para nematoda, uniref e trembl)
                            fasta.setFastaContent(fastaContent.toString());
                            //fasta = (Fasta)save(fasta);
                            fastasToSave.add(fasta);
                            fastaContent = null;
                        }

                        fasta = getMRnaFastaIdentity(linha);

                    }else{
                        if (fastaContent != null) {
                            fastaContent.append(System.getProperty("line.separator"));
                        }else{
                            fastaContent = new StringBuilder();
                        }

                        fastaContent.append(linha);
                    }

                }

                if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                    //Salva o último fasta
                    fasta.setFastaContent(fastaContent.toString());
                    //fasta = (Fasta)save(fasta);
                    fastasToSave.add(fasta);
                }

                System.out.println("TOTAL FASTA ITEMS PARA SALVAR:" + fastasToSave.size());

                for (int i=0; i<fastasToSave.size(); i++) {
                    fasta = (MRnaFasta)save(fastasToSave.get(i));
                    fastas.add(saveMRnaFastaData(fastasToSave.get(i)));
                }

                System.out.println("TOTAL FASTA ITEMS SALVOS:" + fastas.size());

            } catch (FileNotFoundException e) {
                System.out.println ("linhas: " + qtdLinhas);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println ("linhas: " + qtdLinhas);
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        System.out.println ("linhas: " + qtdLinhas);
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    public void importIntragenomicData(String filesPath, String file) throws IOException, InvalidFormatException {

        String arquivo = filesPath + File.separator + file;

        if (arquivo == ""){
            System.out.println("nothing to do");
        }else {

            File ods = new File(arquivo);
            XSSFWorkbook book = new XSSFWorkbook(ods);
            XSSFSheet sheet = book.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            List<Intragenomic> intragenomics = new ArrayList<Intragenomic>();

            // Iterating over Excel file in Java
            while (itr.hasNext()) {

                Row row = itr.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                try{
                    Cell cell = cellIterator.next();

                    String cellValue = cell.getStringCellValue();

                    if (!cellValue.contains("EC")){

                        System.out.print(cellValue + "\t");

                        Intragenomic intragenomic = new Intragenomic();

                        intragenomic.setEc_number(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();

                        String[] seqOrf = cellValue.split("\\|");

                        intragenomic.setSeqName(seqOrf[0]);
                        intragenomic.setOrfsPtns(seqOrf[1]);

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setTpm(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setFpkm(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setEnzymeDescription(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setUniref100(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        double cellDoubleValue = cell.getNumericCellValue();
                        intragenomic.setLength(cellDoubleValue);

                        cell = cellIterator.next();
                        cellDoubleValue = cell.getNumericCellValue();
                        intragenomic.setNumberHits(cellDoubleValue);

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.seteValue(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();

                        try{
                            cellValue = cell.getStringCellValue();
                            if (cellValue!=null && cellValue != ""){
                                intragenomic.setSimMean(Double.parseDouble(cellValue.replaceAll("%","").replaceAll("'","")));
                            }
                        }catch (IllegalStateException e){
                            cellDoubleValue = cell.getNumericCellValue();
                            intragenomic.setSimMean(cellDoubleValue);
                        }

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setFold(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setSuperfamily(cellValue.replaceAll("'",""));

                        cell = cellIterator.next();
                        cellValue = cell.getStringCellValue();
                        intragenomic.setLiterature_function(cellValue.replaceAll("'",""));

                        intragenomics.add((Intragenomic)save(intragenomic));
                    }

                    System.out.println("TOTAL ITEMS ON INTRAGENOMIC TABLE:" + intragenomics.size());
                }catch(NoSuchElementException e){
                    System.out.println("TOTAL ITEMS ON INTRAGENOMIC TABLE:" + intragenomics.size());
                }

            }
        }
    }


    /*public void importIntragenomicDataCsv(String filesPath, String file) {

        String arquivoCSV = filesPath + File.separator + file;

        if (arquivoCSV == ""){
            System.out.println("nothing to do");
        }else{

            BufferedReader br = null;
            String linha = "";
            String csvDivisor = "\\t";
            List<Intragenomic> intragenomics = new ArrayList<Intragenomic>();
            boolean header = true;

            try {

                br = new BufferedReader(new FileReader(arquivoCSV));
                while ((linha = br.readLine()) != null) {
                    String[] line = linha.split(csvDivisor);
                    if (line[0].contains("EC")){
                        header = false;
                    }else{
                        intragenomics.add((Intragenomic)save(getIntragenomicData(line)));
                    }
                }

                System.out.println("TOTAL ITEMS ON INTRAGENOMIC TABLE:" + intragenomics.size());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }*/


}