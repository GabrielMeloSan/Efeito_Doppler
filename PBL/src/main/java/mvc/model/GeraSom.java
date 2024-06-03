package mvc.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class GeraSom {
    public static void CriaAudio(double f1, double f2, double duracao, String nome) {

        SenoECosseno calc = new SenoECosseno();
        // Define as frequências desejadas em Hz
        double frequencia1 = f1; // Exemplo: 440 Hz para a nota Lá
        double frequencia2 = f2; // Nova frequência para a segunda metade

        // Define a duração total do áudio em segundos
        double duracaoTotal = duracao; // Exemplo: 6 segundos

        // Define o volume inicial, final e o momento de mudança de volume (em segundos)
        double volumeInicial = 0.1; // Volume inicial (entre 0.0 e 1.0)
        double volumeFinal = 1.0; // Volume máximo
        double momentoMudancaVolume = duracaoTotal / 2.0; // Momento de mudança de volume (na metade)

        // Define o nome do arquivo de saída
        String nomeArquivo = nome + ".wav";

        try {
            // Obtém o formato de áudio padrão
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);

            // Cria um stream de saída de áudio
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] samples = new byte[(int) (44100 * duracaoTotal) * 2]; // * 2 pois cada amostra é de 16 bits (2 bytes)

            // Gera o áudio
            for (int i = 0; i < samples.length / 2; i += 2) {
                double volume;
                if (i / 2 < momentoMudancaVolume * 44100) {
                    // Se estamos na primeira metade do áudio
                    volume = volumeInicial + ((volumeFinal - volumeInicial) * (i / 2) / (momentoMudancaVolume * 44100));
                } else {
                    // Se estamos na segunda metade do áudio
                    volume = volumeFinal - ((volumeFinal - volumeInicial) * ((i / 2) - (momentoMudancaVolume * 44100)) / (momentoMudancaVolume * 44100));
                }
                double angle1 = 1.0 * Math.PI * i / (44100 / frequencia1);
                short sample1 = (short) (calc.CalculaSeno(angle1) * volume * Short.MAX_VALUE);
                samples[i] = (byte) (sample1 & 0xFF);
                samples[i + 1] = (byte) ((sample1 >> 8) & 0xFF);
            }

            for (int i = samples.length / 2; i < samples.length; i += 2) {
                double volume;
                if ((i - samples.length / 2) / 2 < momentoMudancaVolume * 44100) {
                    // Se estamos na terceira metade do áudio
                    volume = volumeFinal - ((volumeFinal - volumeInicial) * ((i - samples.length / 2) / 2) / (momentoMudancaVolume * 44100));
                } else {
                    // Se estamos na quarta metade do áudio
                    volume = volumeInicial + ((volumeFinal - volumeInicial) * (((i - samples.length / 2) / 2) - (momentoMudancaVolume * 44100)) / (momentoMudancaVolume * 44100));
                }
                double angle2 = 2.0 * Math.PI * ((i - samples.length / 2) / 2) / (44100 / frequencia2);
                short sample2 = (short) (calc.CalculaSeno(angle2) * volume * Short.MAX_VALUE);
                samples[i] = (byte) (sample2 & 0xFF);
                samples[i + 1] = (byte) ((sample2 >> 8) & 0xFF);
            }

            // Escreve os dados de áudio no stream de saída
            outputStream.write(samples, 0, samples.length);

            // Cria um stream de áudio de entrada a partir do stream de saída
            AudioInputStream audioInputStream = new AudioInputStream(new ByteArrayInputStream(outputStream.toByteArray()), format, (long) (44100 * duracaoTotal));

            // Salva o arquivo de áudio
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(nomeArquivo));

            // Fecha os streams
            outputStream.close();
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}