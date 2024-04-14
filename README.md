# transito-rio-android-kotlin
Projeto que consulta a API do Data.rio para pegar a posição da frota de ônibus em circulação do Rio de Janeiro, o app amostra a posição dos ônibus nos últimos 10 segundos.

<br>

O App roda a partir do Android 8.0 (API Level 26), usa a arquitetura MVVM, injeção de dependência e a API de localização do SDK.

<hr>

Dependências:
-------------
O projeto usa as seguintes dependência:

- Google Maps API;
- Koin;
- LifeCycle;
- Material Design;
- Navigation;
- Retrofit;

Configuração:
-------------
Criar o arquivo `secrets.properties` no diretório raiz do projeto e colocar a propriedade `MAPS_API_KEY` com a API_KEY do Google Maps. <br>

Exmpelo:<br>
`MAPS_API_KEY=<API_KEY_DO_GOOGLE_MAPS>`

<hr>

Disponível na Play Store: <br>
https://play.google.com/store/apps/details?id=br.com.rodrigoamora.transitorio
