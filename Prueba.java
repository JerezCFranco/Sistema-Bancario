import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class Cliente {
    private String nombre;
    private String direccion;
    private String contrasena;
    public double saldoTotal;
    private List<CuentaBancaria> cuentas;

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public void eliminarCuenta(CuentaBancaria cuenta) {
        cuentas.remove(cuenta);
    }

    public double consultarSaldoTotal() {
        for (CuentaBancaria cuenta : cuentas) {
            saldoTotal += cuenta.getSaldo();
        }
        return saldoTotal;
    }

     public boolean verificarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }

    public void agregarSaldo(double cantidad) {
        this.saldoTotal += cantidad;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }
 

    // Método para exportar cuentas a un archivo CSV

    public void exportarCuentasCSV(String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Encabezado del CSV
            writer.write("Numero unico del titular,Nombre de titular,Saldo,Tipo\n");

            // Ordenar las cuentas por número único del titular y saldo
            Collections.sort(cuentas);

            // Escribir las cuentas en el archivo CSV
            for (CuentaBancaria cuenta : cuentas) {
                writer.write(cuenta.getNumeroUnicoTitular() + ","
                        + nombre + ","
                        + cuenta.getSaldo() + ","
                        + cuenta.getTipo() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

abstract class CuentaBancaria implements Comparable<CuentaBancaria> {
    private static int contadorNumerosUnicos = 1;

    private int numeroCuenta;
    private String titular;
    private double saldo;

    public CuentaBancaria(String titular, double saldo) {
        this.numeroCuenta = contadorNumerosUnicos++;
        this.titular = titular;
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract String getTipo();

    public void depositar(double monto) {
        saldo += monto;
    }

    public abstract void retirar(double monto);

    public double consultarSaldo() {
        return saldo;
    }

    public int getNumeroUnicoTitular() {
        return numeroCuenta;
    }

    // Implementación de Comparable para ordenar por número único del titular y saldo
    @Override
    public int compareTo(CuentaBancaria otraCuenta) {
        if (this.numeroCuenta != otraCuenta.numeroCuenta) {
            return Integer.compare(this.numeroCuenta, otraCuenta.numeroCuenta);
        } else {
            return Double.compare(this.saldo, otraCuenta.saldo);
        }
    }
}

class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String titular, double saldo, double tasaInteres) {
        super(titular, saldo);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public String getTipo() {
        return "Cuenta de Ahorro";
    }

    @Override
    public void retirar(double monto) {
        // Implementación específica para cuenta de ahorro
        if (monto <= consultarSaldo()) {
            super.retirar(monto);
        } else {
            System.out.println("Saldo insuficiente para el retiro");
        }
    }

    public void agregarIntereses() {
        depositar(consultarSaldo() * tasaInteres);
    }
}

class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro;

    public CuentaCorriente(String titular, double saldo, double limiteSobregiro) {
        super(titular, saldo);
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public String getTipo() {
        return "Cuenta Corriente";
    }

    @Override
    public void retirar(double monto) {
        // Implementación específica para cuenta corriente
        double saldoDisponible = consultarSaldo() + limiteSobregiro;
        if (monto <= saldoDisponible) {
            super.retirar(monto);
        } else {
            System.out.println("Saldo insuficiente para el retiro");
        }
    }
}

class Banco {
    private List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    // Otros métodos para gestionar clientes y cuentas en el banco
}
