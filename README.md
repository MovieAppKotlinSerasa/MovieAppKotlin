# MovieApp  
  

MovieApp é um aplicativo Android para gerenciar coleções de filmes.  

  
## Descrição  


A ideia do aplicativo é fornecer ao usuário a possibilidade de pesquisar e salvar seus filmes favoritos em coleções que podem ser acessadas online e offline. Será possível ver a imagem do pôster do filme, e se houver, o trailer disponível no Youtube. Sempre que quiser buscar por filmes novos eles estarão disponíveis no aplicativo, separados por gêneros, sendo possível buscar por nome.   

  

A API que utilizamos para construir o projeto é da [TMDB](https://www.themoviedb.org/) (The Movie Database).   

  

### Problema a ser resolvido, onde queremos chegar e porque isso é importante  


Quanto mais acesso à cultura mais informados e preparados estamos para todas as situações da vida. Conhecimento não ocupa espaço e uma vez que você o possui, ele não pode mais ser tirado de você. Alguns aplicativos disponíveis até tentam, mas nenhum resolve de maneira satisfatória o gerenciamento e o compartilhamento de coleções de filmes e de indicações de filmes para os amigos.   
  

Coleções de filmes favoritos é um recurso que muitos aplicativos têm, mas ainda falta uma integração com os amigos, por exemplo, cruzando os filmes vistos e favoritos de todos os amigos, de um grupo de amigos, ou de um só amigo. Grupos de amigos ou casais poderiam ver suas classificações e anotações, e o aplicativo poderia trazer automaticamente as críticas dos especialistas. É possível buscar tudo isso hoje em sites da internet e em aplicativos individualmente, mas nenhum faz um bom trabalho de inteligência na utilização dessas fontes de dados, cruzando informações.   
   

Para citar mais um exemplo, seria interessante descobrir exatamente qual a predileção de filmes de seus amigos. Ela ou ele gosta mais de filmes de ação ou romance? Será que da "match"? ;-)  


## Telas  


Após uma pequena animação você é direcionado a tela de login, onde é solicitado e-mail e senha :

<img width=300 align=center alt="teladelogin" src="https://user-images.githubusercontent.com/49874215/134971387-7e21d993-897c-4bbe-8790-6c6adf52e600.png">

Caso você ainda não tenha cadastro favor se direcionar a tela de cadastro :

<img  width=300 align=center alt="teladecadastro" src="https://user-images.githubusercontent.com/49874215/134970646-d92d2fe1-b3a7-4348-ae0b-d338883d239d.png">

Após preencher email e confirmar a senha você é direcionado a tela principal,
nesta tela tem um menu no canto superior esquerdo e um menu na parte inferior :

<img width=300 align=center alt="telaprincipal" src="https://user-images.githubusercontent.com/49874215/134970845-23c5c014-4441-4a52-a422-43e4026fb222.png">

Na parte inferior está a tela de pesquisa onde é possível pesquisar os filmes por nome :

<img width=300 align=center alt="teladepesquisa" src="https://user-images.githubusercontent.com/49874215/134970932-db2cc019-1505-4dc4-81e3-6e32b8ad30c9.png">

Ao clicar em um filme são exibidos os detalhes do mesmo,
você pode ver os gêneros nos quais o filme se encaixa, sua nota, sinopse e pode favoritá-lo clicando no coração :

<img width=300 align=center alt="teladedetalhes" src="https://user-images.githubusercontent.com/49874215/134971053-978eb480-7eaa-428f-aed2-a3f73cbcfc48.png">

Aqui ficam os filmes que foram salvos na coleção :

<img width=300 align=center alt="teladefavoritos" src="https://user-images.githubusercontent.com/49874215/134971212-923686e0-d3e2-45af-881f-6c219b497f69.png">

No menu superior é exibido e-mail do usuário e avatar caso queira colocar,
também é apresentada a opção de configuração, notificação, voltar ao menu e sair do app :

<img width=300 align=center alt="menulateral" src="https://user-images.githubusercontent.com/49874215/134972349-e874fdb4-c374-49a7-8a7a-bd605b693a80.png">

Nas configurações você pode manter a sessão e mudar para o modo escuro :

<img width=300 align=center alt="configuracoes" src="https://user-images.githubusercontent.com/49874215/134972592-4f7beb53-cd87-4a03-a035-5008f4b4def2.png">


## Ferramentas utilizadas  

 
- [Android Studio](https://developer.android.com/studio)  

- [Kotlin](https://kotlinlang.org/)  

- [Git](https://git-scm.com/)  

- [GitHub](https://github.com/)  

- [Postman](https://www.postman.com/)  

  

## Instalação  

Use o [Git](https://git-scm.com/) para clonar o repositório e executar o projeto em seu Android Studio.

Ou faça o download da apk [aqui](outputs/app-debug.apk)
  

```bash 

git clone https://github.com/viniciusjasinski/MovieApp.git  

```  


## Roteiro  

 
- MovieApp v0.0.1 alpha  

- MovieApp v0.1.0 beta  

- MovieApp v0.2.0 release candidate  

- MovieApp v1.0.0 final  

  

## Implementações futuras, o que vem pela frente que ainda gostaríamos de disponibilizar no App  


- Trazer notícias sobre cinema, filmes e série; 

- Exibir datas de estreias no cinema, na TV ou no serviço de stream 

- Mostrar sinopses, críticas e curiosidades sobre filmes, séries, episódios, atores, atrizes, diretores, escritores, enfim, todo o elenco; 

- O app poderia descobrir (se o usuário ativou a localização do celular) qual a próxima sessão no cinema mais próximo; 

- Descobrir os cinemas mais próximos; 

- Compartilhar coleções de favoritos entre amigos. 

  

## Autores  
 

- [Danillo Araújo de Souza](https://github.com/dylotrons)  

- [Tatiane Pedrelli](https://github.com/tatianex/)  

- [Vinicius César Jasinski](https://github.com/viniciusjasinski)  

  

## Agradecimentos  
  

Gostaríamos de agradecer imensamente pela oportunidade oferecida pela [Serasa](https://www.serasa.com.br/) que nos proporcionou o curso e apoio para iniciarmos essa jornada de aprendizagem em Android com Kotlin e ao professor [Arthur](https://github.com/arthurcordova) por toda a paciência, atenção e por ter compartilhado conosco seu conhecimento e experiência.  
  

## Licença  

[MIT](https://choosealicense.com/licenses/mit/) 
