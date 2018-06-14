# Android E06: Persistência

Professor: João Eduardo Montandon

## Salvando Fotos no Aparelho

É muito comum que aplicativos de mensagem (WhatsApp, Telegram, etc.) armazenem na memória do dispositivo as fotos que ele recebe.

Você deverá implementar um aplicativo que tire uma foto qualquer, e armazene essa foto na memória externa do dispositivo. Mais especificamente, a tela de seu aplicativo deverá ter: (a) botão para tirar a foto; (b) componente para mostrar o preview da foto (`ImageView`); (c) botão para salvar a foto.

Utilize os links abaixo para auxiliar na implementação do exercício:

* [Extraindo imagem de um `ImageView`](https://stackoverflow.com/questions/9042932/getting-image-from-imageview)
* [Salvar Bitmap em um arquivo](https://stackoverflow.com/questions/649154/save-bitmap-to-location)

## Registrando último acesso

Os desenvolvedores costumam utilizar o recurso de `SharedPreferences` para armazenar, entre outras informações, a data/hora referente ao último acesso do usuário no aplicativo.

Você deverá adicionar um registro na inicialização do aplicativo para armazenar e exibir a data/hora do último acesso. Mais especificamente, o comportamento do aplicativo deverá seguir a seguinte lógica:

* Toda vez que o usuário abrir o aplicativo, um `Toast` deverá ser exibido informando a data e hora do último acesso no formato **dd/mm/yyyy - HH:MM**.
* No primeiro acesso o aplicativo deverá **apenas** registrar a data e hora do acesso.

## Gravando Informações do Usuário no Banco

Implemente uma nova tela no aplicativo que fará o armazenamento de produtos. Essa tela deverá conter o formulário com dois campos: (a) nome do produto; (b) preço do produto.

Você deverá implementar uma classe chamada `ProdutoDAO` que será responsável pelo armazenamento e leitura dos produtos cadastrados anteriormente. Sua classe deverá ter um método `void insert(Produto p)` para fazer a inserção do produto e `List<Produto> getAll()` para retornar todos os produtos cadastrados.