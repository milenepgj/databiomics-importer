package app.controller;


import app.dto.Employee;
import app.dto.Sequence;
import app.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class LeiaCVS {

    public static void main(String[] args) {

        LeiaCVS obj = new LeiaCVS();
        obj.run();

    }

    private Sequence getSequenceData(String[] line){

        Sequence sequence = new Sequence();
        sequence.setSeqName(line[0]);
        sequence.setDescription(line[1]);
        sequence.setLength(Integer.parseInt(line[2]));
        sequence.setNumberHits(Integer.parseInt(line[3]));
        sequence.setEValue(line[4]);
        sequence.setSimMean(Double.parseDouble(line[5].replace("%","").replace("-","0").replace(",",".")));
        sequence.setGo(line[6]);
        sequence.setGoNames(line[7]);
        sequence.setEnzimeCodes(line[8]);
        sequence.setInterproIds(line[9]);

        return sequence;

    }

    private static Sequence save(Sequence sequence) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        session.save(sequence);

        session.getTransaction().commit();

        session.close();

        return sequence;
    }

    public void run() {

        //String arquivoCSV = "/home/milene.guimaraes/Documents/Pessoal/databiomics-importer/Tabela_Anotacao_Angiostrongylus_Nr-NCBI";
        String arquivoCSV = "C:\\Users\\milen_000\\Documents\\_development\\databiomics-importer\\Tabela_Anotacao_Angiostrongylus_Nr-NCBI";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = "\\t";
        List<Sequence> sequences = new ArrayList<Sequence>();
        boolean header = true;

        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {
                String[] line = linha.split(csvDivisor);
                if (line[0].equalsIgnoreCase("SeqName")){
                    header = false;
                }else{
                    sequences.add(save(getSequenceData(line)));
                }
            }

            System.out.println(sequences.size());


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