package modulos.pruduto;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import telas.LoginTela;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@DisplayName("Testes Mobile do Módulo de Produto")
public class ProdutoTest {
    private WebDriver app;

    @BeforeEach
    public void  beforeEcach() throws MalformedURLException {
        //Abrir o App
        DesiredCapabilities capacidades = new DesiredCapabilities();
        capacidades.setCapability("deviceName","Google Pixel 3");
        capacidades.setCapability("platform", "Android");
        capacidades.setCapability("udid","192.168.114.101:5555");
        capacidades.setCapability("appPackage","com.lojinha");                  //COMANDO CMD: adb shell dumpsys window | findstr /R "mCurrentFocus"
        capacidades.setCapability("appActivity","com.lojinha.ui.MainActivity"); //COMANDO CMD: adb shell dumpsys window | findstr /R "mCurrentFocus"
        capacidades.setCapability("app","C:\\Android\\LojinhaAndroidNativa\\lojinha-nativa.apk");

        this.app = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capacidades);
        this.app.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
    }

    @DisplayName("Validação do valor de produto não permitido")
    @Test
    public void testValidacaoDoValorDeProdutoNaoPermitido() {

        //Fazer Login
        String mensagemApresentada = new LoginTela(app)
                .preencherUsuario("admin")
                .preencherSenha("admin")
                .submeterLogin()
                .abrirTelaAdicaoProduto()
                .preencherNomeProduto("iPhone")
                .preencherValorProduto("700001")
                .preencherCorProduto("Rosa")
                .submissaoComErro()
                .obterMensagemDeErro();

        //Validar que a mensagem de valor inválido foi apresentada  - com.lojinha:id/button
        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00",mensagemApresentada);
    }

    @AfterEach
    public void afterEach(){
        app.quit();
    }
}