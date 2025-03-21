package Programa;

import model.Conta;
import model.Pessoa;
import utils.Utils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AgenciaBancaria {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Conta> contasBancarias;

    public static void main(String[] args) {
        contasBancarias = new ArrayList<>();
        operacoes();
    }

    public static void operacoes() {
        System.out.println("------------------------------------------------------");
        System.out.println("------------- Bem vindos à nossa Agência Soares --------------");
        System.out.println("------------------------------------------------------");
        System.out.println("***** Selecione uma operação que deseja realizar *****");
        System.out.println("| 1 - Criar Conta         |");
        System.out.println("| 2 - Depositar           |");
        System.out.println("| 3 - Sacar               |");
        System.out.println("| 4 - Transferir          |");
        System.out.println("| 5 - Pix                 |");
        System.out.println("| 6 - Cofrinho            |");
        System.out.println("| 7 - Listar Contas       |");
        System.out.println("| 8 - Sair                |");
        System.out.println("------------------------------------------------------");

        int operacao = obterInteiroValido();
        switch (operacao) {
            case 1 -> criarConta();
            case 2 -> depositar();
            case 3 -> sacar();
            case 4 -> transferir();
            case 5 -> pix();
            case 6 -> gerenciarCofrinho();
            case 7 -> listarContas();
            case 8 -> {
                System.out.println("Obrigado por usar nossa agência. Até mais!");
                System.exit(0);
            }
            default -> {
                System.out.println("Opção inválida!");
                operacoes();
            }
        }
    }

    public static void criarConta() {
        System.out.println("Nome: ");
        String nome = input.nextLine();

        System.out.println("CPF: ");
        String cpf = input.nextLine();

        System.out.println("Email: ");
        String email = input.nextLine();

        Pessoa cliente = new Pessoa(nome, cpf, email);
        Conta conta = new Conta(cliente);
        contasBancarias.add(conta);

        System.out.println("--- Conta criada com sucesso! ---");
        System.out.println("Número da Conta: " + conta.getNumeroConta());
        System.out.println("Agência: 001");

        operacoes();
    }

    private static Conta encontrarConta(int numeroConta) {
        for (Conta conta : contasBancarias) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    public static void depositar() {
        int numeroConta = obterInteiroValido("Número da conta: ");
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("Valor para depósito: ");
            Double valor = obterValorValido();
            conta.depositar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
        operacoes();
    }

    public static void sacar() {
        int numeroConta = obterInteiroValido("Número da conta: ");
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("Valor para saque: ");
            Double valor = obterValorValido();
            conta.sacar(valor);
        } else {
            System.out.println("Conta não encontrada.");
        }
        operacoes();
    }

    public static void transferir() {
        int numeroContaRemetente = obterInteiroValido("Número da conta remetente: ");
        Conta contaRemetente = encontrarConta(numeroContaRemetente);

        if (contaRemetente != null) {
            int numeroContaDestinatario = obterInteiroValido("Número da conta destinatário: ");
            Conta contaDestinatario = encontrarConta(numeroContaDestinatario);

            if (contaDestinatario != null) {
                System.out.println("Valor para transferência: ");
                Double valor = obterValorValido();
                contaRemetente.transferencia(contaDestinatario, valor);
            } else {
                System.out.println("Conta destinatária não encontrada.");
            }
        } else {
            System.out.println("Conta remetente não encontrada.");
        }
        operacoes();
    }

    public static void pix() {
        int numeroConta = obterInteiroValido("Número da conta: ");
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("Chave Pix (CPF ou Email): ");
            String chavePix = input.nextLine();
            System.out.println("Valor: ");
            Double valor = obterValorValido();
            conta.sacar(valor);
            System.out.println("Pix enviado para " + chavePix + " com sucesso!");
        } else {
            System.out.println("Conta não encontrada.");
        }
        operacoes();
    }

    public static void listarContas() {
        if (contasBancarias.isEmpty()) {
            System.out.println("Não há contas cadastradas.");
        } else {
            for (Conta conta : contasBancarias) {
                System.out.println(conta);
            }
        }
        operacoes();
    }

    public static void gerenciarCofrinho() {
        int numeroConta = obterInteiroValido("Número da conta: ");
        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {
            System.out.println("1 - Depositar no Cofrinho");
            System.out.println("2 - Sacar do Cofrinho");
            int escolha = obterInteiroValido();

            System.out.println("Valor: ");
            Double valor = obterValorValido();

            if (escolha == 1) conta.depositarCofrinho(valor);
            else if (escolha == 2) conta.sacarCofrinho(valor);
            else System.out.println("Opção inválida.");
        } else {
            System.out.println("Conta não encontrada.");
        }
        operacoes();
    }

    // Método para obter um valor inteiro válido
    private static int obterInteiroValido() {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite novamente.");
                input.nextLine(); // Limpa o buffer
            }
        }
    }

    private static int obterInteiroValido(String mensagem) {
        System.out.println(mensagem);
        return obterInteiroValido();
    }

    // Método para obter um valor decimal válido
    private static Double obterValorValido() {
        while (true) {
            try {
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Valor inválido. Digite um número válido.");
                input.nextLine(); // Limpa o buffer
            }
        }
    }
}
