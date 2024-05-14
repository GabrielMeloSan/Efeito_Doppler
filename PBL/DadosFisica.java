// o planejamento é fazer uma subclasse para o calculo das velocidades e intensidade para serem inseridas posteriormente nesta classe.
// E fazer subclasses para os calculos, geração de graficos e arquivos de audio.


public class DadosFisica {
    // Atributos de entrada e saída
    private double distanciaInicial;
    private double tempo;
    private double velocidadeRelativa;
    private double velocidadeFonte;
    private double potencia;
    private double intensidade;
    private double frequenciaInicial;
    private double frequenciaPercebidaAprox;
    private double frequenciaPercebidaAfast;
    private double nome_do_audio;

    //O método construtor pode sofrer mudanças, pois na hora de instanciar a classe os valores podem ser nulos e o erro acontecer
    //Se der errado o método construtor na main é só apagar os valores de entrada  e colocar valores arbitrários.

    public DadosFisica(double frequenciaInicial, double distanciaInicial, double tempo, double velocidadeRelativa, double potencia, double intensidade,  double nome_do_audio){
        this.frequenciaInicial = frequenciaInicial;
        this.distanciaInicial = distanciaInicial;
        this.tempo = tempo;
        this.velocidadeRelativa = velocidadeRelativa;
        this.potencia = potencia;
        this.intensidade = intensidade;
        this.nome_do_audio = nome_do_audio;
    }

    //métodos get
    public double getFrequenciaInicial(){
        return frequenciaInicial;
    }
    public double getDistanciaInicial(){
        return distanciaInicial;
    }

    public double getTempo(){
        return tempo;
    }

    public double getVelocidadeRelativa(){
        return velocidadeRelativa;
    }

    public double getPotencia(){
        return potencia;
    }

    public double getIntensidade(){
        return intensidade;
    }

    public double getNome_do_audio(){
        return nome_do_audio;
    }

    //métodos set
    public void setFrequenciaInicial(double frequenciaInicial){
        this.frequenciaInicial = frequenciaInicial;
    }
    public void setDistanciaInicial(double distanciaInicial){
        this.distanciaInicial = distanciaInicial;
    }

    public void setTempo(double tempo){
        this.tempo = tempo;
    }

    public void setVelocidadeRelativa(double velocidadeRelativa){
        this.velocidadeRelativa = velocidadeRelativa;
    }

    public void setPotencia(double potencia){
        this.potencia = potencia;
    }

    public void setIntensidade(double intensidade){
        this.intensidade = intensidade;
    }

    public void setNome_do_audio(double nome_do_audio){
        this.nome_do_audio = nome_do_audio;
    }

    //Calculos de Fisica
    public double calculaFrequenciaAprox(double velocidadeInicial,double frequenciaInicial) {

        frequenciaPercebidaAprox = frequenciaInicial * 340 / (340 - velocidadeInicial);

        return frequenciaPercebidaAprox;
    }

    public double calculaFrequenciaAfast(double velocidadeInicial,double frequenciaInicial) {

        frequenciaPercebidaAfast = frequenciaInicial * 340 / (340 + velocidadeInicial);

        return frequenciaPercebidaAfast;
    }

    public double calculaAmplitude(double potencia, double distancia) {

        double intensidadeFisica, amplitude;

        intensidadeFisica = potencia / 4 * Math.PI * Math.pow(distancia, 2);

        amplitude = 10 * Math.log10(intensidadeFisica / Math.pow(10, -12));
        
        return amplitude;

    }

    
}