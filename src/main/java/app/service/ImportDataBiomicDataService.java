package app.service;


import app.dto.Sequence;
import app.dto.Trembl;
import app.dto.Uniref100;
import app.dto.UnirefNematoda;
import app.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    private static Object save(Object object) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        session.saveOrUpdate(object);

        session.getTransaction().commit();

        session.close();

        return object;
    }

}