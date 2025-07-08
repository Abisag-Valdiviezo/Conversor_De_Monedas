import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ConsultarMoneda consultaMoneda = new ConsultarMoneda();
        int opcion;

        do {
            muestraMenu();
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea

            switch (opcion) {
                case 1:
                    convertirMoneda(scanner, consultaMoneda, "USD", "ARS");
                    break;
                case 2:
                    convertirMoneda(scanner, consultaMoneda, "ARS", "USD");
                    break;
                case 3:
                    convertirMoneda(scanner, consultaMoneda, "USD", "BRL");
                    break;
                case 4:
                    convertirMoneda(scanner, consultaMoneda, "BRL", "USD");
                    break;
                case 5:
                    convertirMoneda(scanner, consultaMoneda, "USD", "COP");
                    break;
                case 6:
                    convertirMoneda(scanner, consultaMoneda, "COP", "USD");
                    break;
                case 7:
                    System.out.println("¡Gracias por usar el conversor de monedas!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elija una opción del menú.");
            }
            System.out.println(); // Línea en blanco para mejor visualización
        } while (opcion != 7);

        scanner.close();
    }

    public static void muestraMenu() {
        System.out.println("""
            ******************************************
            Bienvenido al conversor de monedas

            1) Dolar =>> Peso Argentino
            2) Peso Argentino =>> Dolar
            3) Dolar =>> Real Brasileno
            4) Real Brasileno =>> Dolar
            5) Dolar =>> Peso Colombiano
            6) Peso Colombiano =>> Dolar
            7) Salir

            Elija una opcion valida
            ******************************************
            """);
    }

    public static void convertirMoneda(Scanner scanner, ConsultarMoneda consultaMoneda, String monedaBase, String monedaDestino) {
        System.out.print("Ingrese la cantidad de " + monedaBase + " a convertir: ");
        double cantidad = scanner.nextDouble();
        scanner.nextLine(); // Consumir la nueva línea

        try {
            Moneda monedaData = consultaMoneda.obtenerTasasDeCambio(monedaBase);
            if (monedaData != null && monedaData.conversion_rates() != null && monedaData.conversion_rates().containsKey(monedaDestino)) {
                double tasaCambio = monedaData.conversion_rates().get(monedaDestino);
                double resultado = cantidad * tasaCambio;
                System.out.printf("%.2f %s son %.2f %s\n", cantidad, monedaBase, resultado, monedaDestino);
            } else {
                System.out.println("No se pudo obtener la tasa de cambio para " + monedaBase + " a " + monedaDestino + ".");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ocurrió un error al realizar la consulta: " + e.getMessage());
        }
    }
}