//deletado a parte "package PBL;"



// o planejamento é fazer uma subclasse para o calculo das velocidades e intensidade para serem inseridas posteriormente nesta classe.
// E fazer subclasses para os calculos, geração de graficos e arquivos de audio.

public class DadosFisica {
    // Atributos de entrada e saída
    private double frequencia0;
    private double distancia_inicial;
    private double tempo;
    private double velocidade_relativa;
    private double potencia;
    private double intensidade;
    private double frequencia_saida;
    private double nome_do_audio;

    //O método construtor pode sofrer mudanças, pois na hora de instanciar a classe os valores podem ser nulos e o erro acontecer 
    //Se der errado o método construtor na main é só apagar os valores de entrada  e colocar valores arbitrários.

    public DadosFisica(double frequencia0, double distancia_inicial, double tempo, double velocidade_relativa, double potencia, double intensidade, double frequencia_saida, double nome_do_audio){
        this.frequencia0 = frequencia0;
        this.distancia_inicial = distancia_inicial;
        this.tempo = tempo;
        this.velocidade_relativa = velocidade_relativa;
        this.potencia = potencia;
        this.intensidade = intensidade;
        this.frequencia_saida = frequencia_saida;
        this.nome_do_audio = nome_do_audio;
    }

    //métodos get
    public double getFrequencia0(){
        return frequencia0;
    }

    public double getDistancia_inicial(){
        return distancia_inicial;
    }

    public double getTempo(){
        return tempo;
    }

    public double getVelocidade_relativa(){
        return velocidade_relativa;
    }

    public double getPotencia(){
        return potencia;
    }

    public double getIntensidade(){
        return intensidade;
    }

    public double getFrequencia_saida(){
        return frequencia_saida;
    }

    public double getNome_do_audio(){
        return nome_do_audio;
    }

    //métodos set
    public void setFrequencia0(double frequencia0){
        this.frequencia0 = frequencia0;
    }

    public void setDistancia_inicial(double distancia_inicial){
        this.distancia_inicial = distancia_inicial;
    }

    public void setTempo(double tempo){
        this.tempo = tempo;
    }

    public void setVelocidade_relativa(double velocidade_relativa){
        this.velocidade_relativa = velocidade_relativa;
    }

    public void setPotencia(double potencia){
        this.potencia = potencia;
    }

    public void setIntensidade(double intensidade){
        this.intensidade = intensidade;
    }

    public void setFrequencia_saida(double frequencia_saida){
        this.frequencia_saida = frequencia_saida;
    }

    public void setNome_do_audio(double nome_do_audio){
        this.nome_do_audio = nome_do_audio;
    }
}
