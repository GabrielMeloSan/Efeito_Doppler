package mvc.model;

public class SenoECosseno {

    //Função para calcular fatorial
    public static int calcularFatorial(int n) {
        int fatorial = 1;
        for (int i = 1; i <= n; i++) {
            fatorial *= i;
        }
        return fatorial;
    }
      // Função que calcula o seno de um ângulo em RADIANOS
      public double CalculaSeno(double x) {
        double precisao = 1e-15;
        double estimativa = 0;
        int k = 0;

        // coloca o angulo entre 0 e 2pi
        x = x % (2 * Math.PI);
        if (x < 0) {
            x += 2 * Math.PI;
        }

        // Se o valor for maior que π, ajusta para o intervalo [-π, π]
        if (x > Math.PI) {
            x -= 2 * Math.PI;
        }

        // Se o valor for maior que π/2 ou menor que -π/2, usa a identidade seno
        if (x > Math.PI / 2) {
            x = Math.PI - x;
        } else if (x < -Math.PI / 2) {
            x = -Math.PI - x;
        }

        // Série de Maclaurin para seno
        double termo = x;  // Primeiro termo da série
        while (Math.abs(termo) > precisao) {
            estimativa += termo;
            k++;
            termo = -termo * x * x / (2 * k * (2 * k + 1));
        }

        return estimativa;
    }
        
    }
