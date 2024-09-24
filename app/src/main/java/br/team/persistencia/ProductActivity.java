package br.team.persistencia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import br.team.persistencia.Classes.Product;
import br.team.persistencia.Utils.ProductDAO;


public class ProductActivity extends AppCompatActivity {

    private TextView lastPage, productSaved;
    private EditText productName, productPrice;
    private Button productSave;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        // Inicializa as views
        lastPage = findViewById(R.id.last_page);
        productSaved = findViewById(R.id.product_saved);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_prize);
        productSave = findViewById(R.id.product_save);

        // Inicializa o DAO (banco de dados)
        productDAO = new ProductDAO(this);

        // Clique no botão de salvar product
        productSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pega os valores de entrada
                String nome = productName.getText().toString();
                String precoStr = productPrice.getText().toString();

                if (nome.isEmpty() || precoStr.isEmpty()) {
                    Toast.makeText(ProductActivity.this, "Por favor, insira todos os dados.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double preco = Double.parseDouble(precoStr);

                // Cria um novo product e inserindo no banco de dados
                Product product = new Product(nome, preco);
                productDAO.insert(product);

                // Exibe uma mensagem de confirmação
                Toast.makeText(ProductActivity.this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();

                // Atualiza a lista de products no TextView
                updateProductList();
            }
        });

        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Atualiza a lista de produtos na inicialização
        updateProductList();
    }

    // Método para atualizar a lista de produtos no TextView
    private void updateProductList() {
        List<Product> produtos = productDAO.getAll();
        StringBuilder produtosTexto = new StringBuilder();
        for (Product produto : produtos) {
            produtosTexto.append(produto.toString()).append("\n");
        }

        // Definindo o texto no TextView
        productSaved.setText(produtosTexto.toString());
        productSaved.setVisibility(View.VISIBLE);
    }
}