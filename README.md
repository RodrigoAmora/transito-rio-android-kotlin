# transito-rio-android-kotlin
Descrição:
----------
Projeto que consulta a API do Data.rio para pegar a posição da frota de ônibus em circulação do Rio de Janeiro, o app amostra a posição dos ônibus em tempo real.

<br>

O App roda a partir do Android 12 (API Level 31), usa a arquitetura MVVM, injeção de dependência e a API de localização do SDK.

Dependências:
-------------
O projeto usa as seguintes dependência:

- Google Maps;
- Koin;
- LifeCycle;
- Material Design;
- Navigation;
- OkHTTP;
- Retrofit;

Configuração:
-------------
Criar o arquivo `secrets.properties` no diretório raiz do projeto e colocar a propriedade `MAPS_API_KEY` com a API_KEY do Google Maps. <br>

Exmpelo:<br>
`MAPS_API_KEY=<API_KEY_DO_GOOGLE_MAPS>`

Autor:
------
<b>Rodrigo Amora</b>

LinkedIn: https://linkedin.com/in/rodrigoamora <br>
E-mail: rodrigo.amora.freitas@gmail.com

Acesso ao projeto:
------------------
Disponível na Play Store: <br>
https://play.google.com/store/apps/details?id=br.com.rodrigoamora.transitorio
