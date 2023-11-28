import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco();

        // Crear un cliente de ejemplo
        Cliente cliente = new Cliente("Juan", "Calle 123");
        banco.registrarCliente(cliente);

        // Bucle para operaciones repetitivas
        boolean continuar = true;
        while (continuar) {
            System.out.println("1. Agregar cuenta");
            System.out.println("2. Eliminar cuenta");
            System.out.println("3. Consultar saldo total");
            System.out.println("4. Agregar saldo a una cuenta");
            System.out.println("5. Exportar cuentas a CSV");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Agregar cuenta
                    System.out.print("Ingrese el tipo de cuenta (Ahorro/Corriente): ");
                    String tipoCuenta = scanner.nextLine();
                    CuentaBancaria nuevaCuenta;
                    if ("Ahorro".equalsIgnoreCase(tipoCuenta)) {
                        nuevaCuenta = new CuentaAhorro(cliente.getNombre(), 0, 0.05);
                    } else if ("Corriente".equalsIgnoreCase(tipoCuenta)) {
                        nuevaCuenta = new CuentaCorriente(cliente.getNombre(), 0, 500);
                    } else {
                        System.out.println("Tipo de cuenta no válido");
                        continue;
                    }
                    cliente.agregarCuenta(nuevaCuenta);
                    System.out.println("Cuenta agregada con éxito.");
                    break;

                case 2:
                    // Eliminar cuenta
                    System.out.print("Ingrese el número de cuenta a eliminar: ");
                    int numeroCuentaEliminar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    CuentaBancaria cuentaEliminar = null;
                    for (CuentaBancaria cuenta : cliente.getCuentas()) {
                        if (cuenta.getNumeroUnicoTitular() == numeroCuentaEliminar) {
                            cuentaEliminar = cuenta;
                            break;
                        }
                    }
                    if (cuentaEliminar != null) {
                        cliente.eliminarCuenta(cuentaEliminar);
                        System.out.println("Cuenta eliminada con éxito.");
                    } else {
                        System.out.println("No se encontró la cuenta con ese número.");
                    }
                    break;

                case 3:
                    // Consultar saldo total
                    double saldoTotal = cliente.consultarSaldoTotal();
                    System.out.println("Saldo total: " + saldoTotal);
                    break;

                case 4:
                    // Agregar saldo a una cuenta
                    System.out.print("Ingrese la cantidad de saldo a agregar: ");
                    double cantidadSaldoAgregar = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar el buffer

                    cliente.agregarSaldo(cantidadSaldoAgregar);
                    System.out.println("Saldo agregado con éxito a la cuenta.");
                    break;

                case 5:

                    cliente.exportarCuentasCSV("cuentas_bancarias.csv");
                    System.out.println("Cuentas exportadas a 'cuentas_bancarias.csv'");
                    break;

                case 6:

                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }

        // Cerrar el scanner
        scanner.close();
    }

    private static CuentaBancaria buscarCuentaPorNumero(Cliente cliente, int numeroCuenta) {
        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            if (cuenta.getNumeroUnicoTitular() == numeroCuenta) {
                return cuenta;
            }
        }
        return null;
    }

}