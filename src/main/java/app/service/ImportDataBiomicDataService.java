package app.service;


import app.dto.*;
import app.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImportDataBiomicDataService {

    public static void main(String[] args) {

        ImportDataBiomicDataService obj = new ImportDataBiomicDataService();
        //obj.importSequencesData("NCBI");
        obj.importSequencesData("NEMATODA");
        obj.importSequencesData("UNIREF100");
        obj.importSequencesData("TREMBL");

    }

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

    public void importSequencesData(String type) {

        String arquivoCSV = "";

        switch (type){
            case "NCBI":
                arquivoCSV = "C:\\Users\\milen_000\\Documents\\_development\\databiomics-importer\\Tabela_Anotacao_Angiostrongylus_NCBI.txt";
                break;
            case "NEMATODA":
                arquivoCSV = "C:\\Users\\milen_000\\Documents\\_development\\databiomics-importer\\Tabela_Anotacao_Angiostrongylus_Nematoda.txt";
                break;
            case "UNIREF100":
                arquivoCSV = "C:\\Users\\milen_000\\Documents\\_development\\databiomics-importer\\Tabela_Anotacao_Angiostrongylus_Uniref100.txt";
                break;
            case "TREMBL":
                arquivoCSV = "C:\\Users\\milen_000\\Documents\\_development\\databiomics-importer\\Tabela_Anotacao_Angiostrongylus_Trembl.txt";
                break;
        }

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
            String fastaContent = "";
            Fasta fasta = null;
            int qtdLinhas = 0;
            try {

                br = new BufferedReader(new FileReader(filesPath + File.separator + file));
                while ((linha = br.readLine()) != null) {

                    qtdLinhas++;

                    if (linha.contains(sequenceCharIdentity)){

                        if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                            //Salva o fasta (para nematoda, uniref e trembl)
                            fasta.setFastaContent(fastaContent);
                            //fasta = (Fasta)save(fasta);
                            fastasToSave.add(fasta);

                            //fastas = saveFastaData(fasta, fastaContent, fastas);
                        }

                        fasta = getFastaIdentity(linha);

                    }else{
                        if (fastaContent != "") fastaContent += System.getProperty("line.separator");
                        fastaContent += linha;
                    }

                }

                if (fasta != null && !fasta.getSeqName().equals("") && !fastaContent.equals("")){
                    //Salva o último fasta
                    //fastas = saveFastaData(fasta, fastaContent, fastas);

                    fasta.setFastaContent(fastaContent);
                    //fasta = (Fasta)save(fasta);
                    fastasToSave.add(fasta);
                }

                System.out.println("TOTAL FASTA ITEMS PARA SALVAR:" + fastasToSave.size());

                for (int i=0; i<fastasToSave.size(); i++) {
                    fasta = (Fasta)save(fastasToSave.get(i));
                    fastas = saveFastaData(fastasToSave.get(i), fastaContent, fastas);
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

    private List<Fasta> saveFastaData(Fasta fasta, String fastaContent, List<Fasta> fastas){

        //Salva o fasta (para nematoda, uniref e trembl)
/*        fasta.setFastaContent(fastaContent);
        fasta = (Fasta)save(fasta);
        fastas.add(fasta);*/

        saveFastaDataByClassName("NEMATODA", fasta);
        saveFastaDataByClassName("UNIREF100", fasta);
        saveFastaDataByClassName("TREMBL", fasta);

        return fastas;
    }

    private Fasta getFastaIdentity (String linha){
        String[] sequenceIdentity = linha.split("\\|");
        String seqName = sequenceIdentity[0].replaceAll(">","");
        String orfsPtns = sequenceIdentity[1];
        String fastaDescription = sequenceIdentity[2];

        return getFastaData(seqName, orfsPtns, fastaDescription, "");
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
                //returnObject = (UnirefNematoda)session.get(UnirefNematoda.class, seqName);
                hql = hql.replaceAll("table", "UnirefNematoda");

                returnObject = session.createQuery(hql)
                        .setProperties(fasta).uniqueResult();

            case "UNIREF100":
                //returnObject = (Uniref100)session.get(Uniref100.class, seqName);
                hql = hql.replaceAll("table", "Uniref100");

                returnObject = session.createQuery(hql)
                        .setProperties(fasta).uniqueResult();

            case "TREMBL":
                //returnObject = (Trembl)session.get(Trembl.class, seqName);
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
                save(unirefNematoda);
            case "UNIREF100":
                Uniref100 uniref100 = (Uniref100) getItemByFasta("UNIREF100", fasta);
                if (uniref100 != null) {
                    uniref100.setFastaContent(fasta.getFastaContent());
                    uniref100.setFastaDescription(fasta.getFastaDescription());
                }
                save(uniref100);
            case "TREMBL":
                Trembl trembl = (Trembl) getItemByFasta("TREMBL", fasta);
                if (trembl != null) {
                    trembl.setFastaContent(fasta.getFastaContent());
                    trembl.setFastaDescription(fasta.getFastaDescription());
                }
                save(trembl);
        }
        session.close();

        return returnObject;

    }




}