
package mvc.model;

import lib.StdDraw;

public class Grafico {
    public void plot() {      

        int n = 300;

        //Gráfico da Intensidade

        // the function y = sin(4x) + sin(20x), sampled at n+1 points
        // between x = 0 and x = pi
        double[] x = new double[n + 1];
        double[] y = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            x[i] = Math.PI * i / n;
            y[i] = Math.sin(4 * x[i]);
        }

        // Set the canvas size
        StdDraw.setCanvasSize(800, 600);

        // Define margins
        double xMargin = 0.1 * Math.PI;
        double yMargin = 0.2;

        // rescale the coordinate system with margins
        StdDraw.setXscale(-xMargin, Math.PI + xMargin);
        StdDraw.setYscale(-2.1 - yMargin, +2.1 + yMargin);

        // plot the x-axis with tick marks and labels
        StdDraw.line(0, 0, Math.PI, 0);
        for (double i = 0; i <= Math.PI; i += Math.PI / 4) {
            StdDraw.line(i, -0.05, i, 0.05);
            StdDraw.text(i, -0.1, String.format("%.2f", i));
        }

        // plot the y-axis with tick marks and labels
        StdDraw.line(0, -2.1, 0, +2.1);
        for (double i = -2.0; i <= 2.0; i += 0.5) {
            StdDraw.line(-0.05, i, 0.05, i);
            StdDraw.text(-0.15, i, String.format("%.1f", i));
        }

        // plot the approximation to the function
        for (int i = 0; i < n; i++) {
            StdDraw.line(x[i], y[i], x[i + 1], y[i + 1]);
        }
    }
}