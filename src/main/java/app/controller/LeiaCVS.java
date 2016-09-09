package app.controller;


import app.dto.Sequence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeiaCVS {

    public static void main(String[] args) {

        LeiaCVS obj = new LeiaCVS();
        obj.run();

    }

    private Sequence getSequenceData(String[] line){

        Sequence sequence = new Sequence();
        sequence.setSeqname(line[0]);
        sequence.setDescription(line[1]);
        sequence.setLength(Long.parseLong(line[2]));
        sequence.setNumberhits(Long.parseLong(line[3]));
        sequence.setE_value(line[4]);
        sequence.setSim_mean(Double.parseDouble(line[5]));
        sequence.setGo(line[6]);
        sequence.setGo_names(line[7]);
        sequence.setInterpro_ids(line[8]);
        sequence.setSeqname(line[9]);

        return sequence;

    }

    public void run() {

        String arquivoCSV = "/home/milene.guimaraes/Documents/Pessoal/databiomics-importer/Tabela_Anotacao_Angiostrongylus_Nr-NCBI";
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = "\\t";
        List<Sequence> sequences = new ArrayList<Sequence>();

        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            while ((linha = br.readLine()) != null) {

                String[] line = linha.split(csvDivisor);
                sequences.add(getSequenceData(line));

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