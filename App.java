import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException{
        int opcao;
        Scanner scan = new Scanner(System.in);
        List <Produto> produtos = new ArrayList<>();
        List <Venda> vendas = new ArrayList<>();
       
        do {

            System.out.println("\n****\nMENU\n****\n");
            System.out.println("1 - Incluir produto");
            System.out.println("2 - Consultar produto");
            System.out.println("3 - Listagem de produtos");
            System.out.println("4 - Vendas por período - detalhado");
            System.out.println("5 - Realizar venda");
            System.out.println("0 - Sair");
            System.out.println("Opção: ");

            opcao = scan.nextInt();
            scan.nextLine();

            if (opcao == 1) {
                System.out.println("***INCLUSÃO DE PRODUTOS***");
                System.out.println("Insira O nome do produto: ");
                String nome = scan.nextLine();
                System.out.println("Insira o código do produto: ");
                int codigo = scan.nextInt();
                System.out.println("Informe o valor unitario: Ex: ( 3,50 | 2,75 )");
                Double valor = scan.nextDouble();
                System.out.println("Informe a quantidade armazenada no estoque: ");
                int Quantidade = scan.nextInt();
                    
                    for (int i = 0; i < produtos.size(); i++) {
                        if (produtos.get(i).getCodigo() == codigo) {
                            produtos.remove(i);
                        } 
                    }
                        produtos.add(new Produto(codigo, nome, valor, Quantidade));
                        System.out.println("\nProduto incluido com sucesso!!");

                    scan.nextLine();

                    voltarMenu(scan);

            } 
              
              else if (opcao == 2) {

                if (produtos.isEmpty()){
                    System.out.println("Não existem produtos cadastrados no sistema. ");
                }else {

                System.out.println("Digite o codigo do produto a ser buscado: ");
                int codigo = scan.nextInt();

                for (int i = 0; i < produtos.size(); i++) {
                    if (produtos.get(i).getCodigo() == codigo) {
                        System.out.printf("\n---------------\nProduto: %s \nCodigo: %d \nValor: %f \nQuantidade no estoque: %d \n---------------", produtos.get(i).getNome(), produtos.get(i).getCodigo(), produtos.get(i).getValor(), produtos.get(i).getQuantidade());
                    } 
                }
                }
                    scan.nextLine();

                voltarMenu(scan);
            } 
            
              else if (opcao == 3) {

                Double maior = Double.MIN_VALUE, medio = 0.0, menor = Double.MAX_VALUE;
                for (int i = 0; i < produtos.size(); i++) {                
                    if (produtos.get(i).getValor() > maior) {
                        maior = produtos.get(i).getValor();
                    }                    
                     if (produtos.get(i).getValor() < menor) {
                        menor = produtos.get(i).getValor();
                     }
                      medio = produtos.stream().collect(Collectors.averagingDouble(Produto::getValor));
                }              
                      ComparadorPreco comparador = new ComparadorPreco(maior, medio, menor);
                      if (produtos.isEmpty()){
                      System.out.println("Não existem produtos cadastrados no sistema.");
                      } else {
                        System.out.println("\nProdutos:");
                        for (Produto p: produtos){
                        System.out.printf("\n---------------\nProduto: %s \nCodigo: %d \nValor unitario: %f \nQuantidade no estoque: %d\n--------------- ",p.getNome(), p.getCodigo(), p.getValor(), p.getQuantidade());                    
                        }
                        System.out.print(comparador.toString());
                        }
                         voltarMenu(scan);
              }

              else if (opcao == 4) {
                if (vendas.isEmpty()) {
                    System.out.println("\nNão existe nenhum produto cadastrado!");
                } else {              
                    Double maior = Double.MIN_VALUE, medio = 0.0, menor = Double.MAX_VALUE;
                    for (int i = 0; i < produtos.size(); i++){
                
                        if(produtos.get(i).getValor() > maior){
                            maior = produtos.get(i).getValor();
                        }if(produtos.get(i).getValor() < menor){
                         menor = produtos.get(i).getValor();
                        }
                        medio = produtos.stream().collect(Collectors.averagingDouble(Produto::getValor));
    
                    }
                      ComparadorPreco comparador = new ComparadorPreco(maior, medio, menor);

                    vendas.sort(new ComparadorData());
                    String dia = vendas.get(0).getData();
                    String mes = vendas.get(vendas.size() -1).getData();
                    System.out.printf("\nVendas || %s:" , dia, mes);
                
                for(Venda p : vendas){
                    for (int i=0; i < produtos.size(); i++){
                        System.out.printf("\n--------------------\nData: %s\nProduto: %s\nQuantidade obtida no estoque: %d\nCodigo do produto: %d\nValor unitario: 1%s\nValor Total: %s\n-------------------- ", p.getData(), produtos.get(i).getNome(),produtos.get(i).getQuantidade(), produtos.get(i).getCodigo(),produtos.get(i).getValor(), p.getValorTotal());
                    }
                }    
                    System.out.print(comparador.toString());
                    }
                  
                      voltarMenu(scan);   
              } 
              
                else if (opcao == 5){

                   boolean verificar = false;

                   if (produtos.isEmpty()){
                   System.out.println("Não existe nenhum produto cadastrado no sistema para efetuar a venda.");
                   voltarMenu(scan);
                } else{
                    System.out.println("\nInforme a data da venda.");
                    String data = scan.nextLine();
                    Venda venda = new Venda(data, null, null, null, null);

                    while (venda.getData() == "ERRO"){
                    System.out.println("Pressione enter para reiniciar");
                    scan.nextLine();
                    System.out.println("\nInforme a data da venda.");
                    data = scan.nextLine();
                    venda = new Venda(data, null, null, null, null);
                    }
                      System.out.println("informe o codigo do produto: ");
                      int produto = scan.nextInt();
                      System.out.println("informe a quantidade do produto: ");
                      int Quantidade = scan.nextInt();

                      for (int i = 0; i < produtos.size(); i++){
                        
                        if (produtos.get(i).getCodigo() == produto){
                            verificar = true;
                            
                          if (produtos.get(i).getQuantidade() < Quantidade || Quantidade == 0){
                             System.out.println("Quantidade não possuída no estoque!");
                          } else{
                              produtos.get(i).setQuantidade(produtos.get(i).getQuantidade()- Quantidade);
                              Double valor = Quantidade * produtos.get(i).getValor();
                              vendas.add(new Venda(data, valor, valor, valor, valor));
                              if (produtos.get(i).getQuantidade() == 0);
                              System.out.println("Venda concluída!");
                                
                            }
                        }
                  
                    }
                }
                if (verificar == false){
                    System.out.println("Não existe nenhum produto cadastrado com este codigo!");              
                    scan.nextLine();
                    
                  voltarMenu(scan);                  
                }
              }
            
               else if (opcao != 0){  
               System.out.println("\nOpção inválida!");
               }
        } 
        
          while (opcao != 0);

           System.out.println("Fim do programa!");
           scan.close();
    }

            private static void voltarMenu(Scanner scan) throws InterruptedException, IOException{
            System.out.println("\nPressione ENTER para voltar ao menu.");
            scan.nextLine();
        
            if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
            System.out.print("\033[H\033[2J");
            System.out.flush();
    }
}