import java.util.Scanner;

public class BigVigenere {

    private int[] key;
    private char[][] alphabet;
    private char[] cifrado;
    private char[] descrifrado;

    private static int charToPosition(char c) {
        if (c >= 'a' && c <= 'z') return c - 'a';
        if (c >= 'A' && c <= 'Z') return 26 + (c - 'A');
        if (c >= '0' && c <= '9') return 52 + (c - '0');
        if (c == '[') return 62;
        if (c == ']') return 63;
        return -1;
    }

    private char positionToChar(int pos) {
        if(pos < 26) return (char)('a' + pos);
        if(pos < 52) return (char)('A' + (pos - 26));
        if(pos < 62) return (char)('0' + (pos - 52));
        return pos == 62 ? '[' : ']';
    }

    private int charToValue(char a) {
        if (a >= 'a' && a <= 'z') return a - 'a';
        if (a >= 'A' && a <= 'Z') return 26 + (a - 'A');
        if (a >= '0' && a <= '9') return 52 + (a - '0');
        if (a == '[') return 62;
        if (a == ']') return 63;
        return -1;
    }

    private char valueToChar(int b) {
        if(b < 26) return (char)('a' + b);
        if(b < 52) return (char)('A' + (b - 26));
        if(b < 62) return (char)('0' + (b - 52));
        return b== 62 ? '[' : ']';
    }

    public static char[][] BigVigenere() {
        Scanner key= new Scanner(System.in);
        System.out.print("Ingrese la key  ");
        int numericKey=key.nextInt();

        char[][] matriz = new char[62][62];
        for(int i = 0; i < 62; i++) {
            for(int j = 0; j < 62; j++) {
                matriz[i][j] = '0';
            }
        }


        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {

                matriz[i][j] = (char) ('a' + (i + j) % 26);
            }
        }

        int cont3=0;

        for(int i=0;i<52;i++) {
            cont3++;
            int cont4=0;
            for(int j=0;j<52;j++) {
                if(matriz[i][j]=='0') {
                    matriz[i][j] = (char) ('A' + (cont3-1 + cont4) % 26);
                    cont4++;
                }
            }
        }

        int conta=0;

        for(int i=0;i<62;i++) {
            if(i==52) {
                conta=0;
            }
            conta++;
            int contb=0;
            for(int j=0;j<62;j++) {
                if(matriz[i][j]=='0') {
                    matriz[i][j] = (char) ('0' + ((conta-1) + contb) % 10);
                    contb++;
                }
            }
        }

        return matriz;
    }

    public BigVigenere(String numericKey) {
        char[] keypa = numericKey.toCharArray();
        this.key = new int[keypa.length];

        for(int i = 0; i <keypa.length; i++) {
            this.key[i] = charToPosition(keypa[i]);
        }
    }

    public String encrypt(String message) {
        char[]messagearr=message.toCharArray();
        cifrado=new char[messagearr.length];

        for(int i = 0; i < messagearr.length; i++) {
            int pos = charToPosition(messagearr[i]);
            cifrado[i] = positionToChar((pos + key[i % key.length]) % 64);
        }
        return new String(cifrado);
    }

    public String decrypt(String encryptedMessage) {
        char[] descrifrado = encryptedMessage.toCharArray();

        for (int i = 0; i < descrifrado.length; i++) {
            int pos = charToValue(descrifrado[i]);
            if (pos != -1) {
                int ok = key[i % key.length];
                descrifrado[i] = valueToChar((pos - ok + 64) % 64);
            }
        }

        return new String(descrifrado);
    }

    public void reEncrypt() {

        if(cifrado != null) {
            for(int i =0;i<cifrado.length;i++) {
                System.out.print(cifrado[i]);
            }
        }

        if(descrifrado != null) {
            for(int i =0;i<descrifrado.length;i++) {
                System.out.print(descrifrado[i]);
            }
        }

        Scanner clavenueva = new Scanner(System.in);
        System.out.print("Ingrese clave nueva ");
        String nuevaClave = clavenueva.next();


        BigVigenere nuevo = new BigVigenere(nuevaClave);


    }

    public char search(int position) {
        int cont=0;
        for(int i = 0;i<alphabet.length;i++) {
            for(int j=0;j<alphabet[i].length;j++) {
                if(position==cont) {
                    return alphabet[i][j];
                }
                else {
                    cont++;
                }
            }
        }
        return '\0';
    }

    public char optimalSearch(int position) {
        int fila = position / 62;
        int columna = position % 62;
        return alphabet[fila][columna];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Ingrese la clave para el cifrado: ");
        String clave = scanner.nextLine();


        BigVigenere vigenere = new BigVigenere(clave);


        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Cifrar mensaje");
            System.out.println("2. Descifrar mensaje");
            System.out.println("3. Reencriptar con nueva clave");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            if (opcion == 1) {
                System.out.print("Ingrese el mensaje a cifrar: ");
                String mensaje = scanner.nextLine();
                String cifrado = vigenere.encrypt(mensaje);
                System.out.println("Mensaje cifrado: " + cifrado);
            }
            else if (opcion == 2) {
                System.out.print("Ingrese el mensaje a descifrar: ");
                String mensajeCifrado = scanner.nextLine();
                String descifrado = vigenere.decrypt(mensajeCifrado);
                System.out.println("Mensaje descifrado: " + descifrado);
            }
            else if (opcion == 3) {
                vigenere.reEncrypt();
            }
            else if (opcion == 4) {
                System.out.println("Saliendo del programa...");
                scanner.close();
                return;
            }
            else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
