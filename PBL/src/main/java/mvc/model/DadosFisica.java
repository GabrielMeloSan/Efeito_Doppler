package mvc.model;

public class DadosFisica {
    // Atributos de entrada e saída
    private double distanciaInicial;
    private double tempo;
    private double velocidadeRelativa;
    private double potencia;
    private double intensidade;
    private double frequenciaInicial;
    private double frequenciaPercebidaAprox;
    private double frequenciaPercebidaAfast;
    private String nome_do_audio;
    private double [] funcaodografico;

    //Método construtor
    public DadosFisica(double frequenciaInicial, double distanciaInicial, double velocidadeRelativa, double potencia, String nome_do_audio){
        this.frequenciaInicial = frequenciaInicial;
        this.distanciaInicial = distanciaInicial;
        this.velocidadeRelativa = velocidadeRelativa;
        this.potencia = potencia;
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

    public double getFreqPercebidaAprox(){
        return frequenciaPercebidaAprox;
    }

    public double getFreqPercebidaAfast(){
        return frequenciaPercebidaAfast;
    }

    public String getNome_do_audio(){
        return nome_do_audio;
    }

    public double[] getFuncaodografico(){
        return funcaodografico;
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

    public void setNome_do_audio(String nome_do_audio){
        this.nome_do_audio = nome_do_audio;
    }

    //Calculos de Física
    public double CalculaFrequenciaAprox(double velocidadeRelativa,double frequenciaInicial) {

    frequenciaPercebidaAprox = (frequenciaInicial * ((340) / (340 - velocidadeRelativa)));

        return frequenciaPercebidaAprox;
    }

    public double CalculaFrequenciaAfast(double velocidadeRelativa,double frequenciaInicial) {

        frequenciaPercebidaAfast = (frequenciaInicial * ((340) / (340 + velocidadeRelativa)));

        return frequenciaPercebidaAfast;
    }

    public double CalculaAmplitude(double potencia, double distancia) {

        double intensidadeFisica, amplitude;

        intensidadeFisica = potencia / 4 * Math.PI * Math.pow(distancia, 2);

        amplitude = 10 * Math.log10(intensidadeFisica / Math.pow(10, -12));

        return amplitude;

    }

    public void CriaArrayFuncao(double tempo){
        int j = 0;
        while(j<tempo){
            j++;
        }

        this.funcaodografico = new double[j];
    }

    public void Calculafuncao(double tempo){

        CriaArrayFuncao(tempo);
        SenoECosseno sen = new SenoECosseno();
        for(int j = 0; j < this.funcaodografico.length; j++){
            this.funcaodografico[j] = CalculaAmplitude(potencia, (velocidadeRelativa * j)) * sen.CalculaSeno((2* (Math.PI) * frequenciaInicial * j));
        }
    }

    public void CalculaTempoSimulacao(){
        double temposimulacao = 2*distanciaInicial/velocidadeRelativa;

        this.tempo = temposimulacao;
    }

    public void CalculaIntensidade() {
        double intensidadecalculada = potencia / (4 * (Math.PI) * Math.pow(distanciaInicial, 2));
        this.intensidade = intensidadecalculada;
    }

    public double CalculaYt(double t) {
        SenoECosseno sen = new SenoECosseno();
        double time = this.getTempo();
        double y;
        if (t > time / 2.0) {
            y = this.CalculaAmplitude(this.getPotencia(), this.getDistanciaInicial()) * sen.CalculaSeno(6.283185307179586 * this.getFreqPercebidaAfast() * t);
        } else {
            y = this.CalculaAmplitude(this.getPotencia(), this.getDistanciaInicial()) * sen.CalculaSeno(6.283185307179586 * this.getFreqPercebidaAprox() * t);
        }

        return y;
    }

}
