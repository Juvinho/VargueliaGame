package game;

import core.*;
import managers.SoundManager;
import java.util.*;

public class GameEngine {
    private GameWindow window;
    private GameState state;
    private Map<String, Scene> scenes;
    private Scene currentScene;
    private boolean isWaitingForChoice;
    
    public GameEngine(GameWindow window, GameState state) {
        this.window = window;
        this.state = state;
        this.scenes = new HashMap<>();
        this.isWaitingForChoice = false;
        
        // Carregar todas as cenas
        loadScenes();
        
        // Mostrar primeira cena
        showCurrentScene();
    }
    
    private void loadScenes() {
        // Ato 1 - Niuwë
        createNiuweIntroScene();
        createNiuweShowerScene();
        createNiuweAlertScene();
        createNiuwePriorityScene();
        createNiuweHeliScene();
        createNiuweSepharothScene();
        
        // Placeholder para Ato 2
        createEllaStartScene();
        createGameOverScene();
    }
    
    // ========== Ato 1 - Niuwë ==========
    
    private void createNiuweIntroScene() {
        Scene scene = new Scene("NIUWE_INTRO", Player.NIUWE, 
            "VARGUËN INTERNAL TERMINAL v0.1 (1983)\n" +
            "================================================\n\n" +
            "> LOADING LOG_NIUWE...\n" +
            "> ...\n" +
            "> ...\n" +
            "> DECRYPT COMPLETE.\n\n" +
            "Bem-vindo ao arquivo de missão de Niuwë.\n" +
            "Pressione ENTER para começar...\n");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweShowerScene() {
        Scene scene = new Scene("NIUWE_SHOWER", Player.NIUWE,
            "[NIUWË]\n\n" +
            "O dormitório está em paz. Você sai do chuveiro quentinho, " +
            "enrolando-se na toalha, ainda assobiando aquela melodia de jazz " +
            "que tanto gosta. A caixa de som vibra suavemente em cima da mesa.\n\n" +
            "A vida é boa quando não há crises.\n\n" +
            "Mas a vida nunca dura muito aqui em Varguën.\n\n" +
            "Você se aproxima do espelho, secando os fios loiros-queimados " +
            "do seu cabelo. Um sorriso satisfeito cruza seus lábios ao ver " +
            "o reflexo esbelto no vidro. Pelo menos você está esbelto.\n\n" +
            "Pressione ENTER para continuar...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweAlertScene() {
        Scene scene = new Scene("NIUWE_ALERT", Player.NIUWE,
            "[NIUWË]\n\n" +
            "Ao sair do banheiro, você nota algo errado.\n\n" +
            "O céu pela janela não é mais azul escuro com estrelas. " +
            "É vermelho. Um tom rubro e opaco que sangra pelo horizonte, " +
            "como se o próprio firmamento estivesse ferido.\n\n" +
            "— Ué? O que tá acontecendo?\n\n" +
            "Nesse instante, a música é interrompida. O celular começa a vibrar.\n\n" +
            "Você vê o nome na tela:\n" +
            "JORGENSSEN\n\n" +
            "Você sabe o que isso significa.\n" +
            "Você SEMPRE sabe.\n\n" +
            "Pressione ENTER para atender...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuwePriorityScene() {
        Scene scene = new Scene("NIUWE_PRIORITY", Player.NIUWE,
            "[NIUWË]\n\n" +
            "— Sepharoth está vindo. Três cabeças desta vez. — diz Jorgenssen, " +
            "sua voz tensa no outro lado da linha.\n\n" +
            "Você congela. A mão aperta o celular.\n\n" +
            "Há mais de 30 mil alunos em Varguën. A maioria está em pânico. " +
            "Os corredores devem estar um caos agora.\n\n" +
            "Você tem apenas minutos para decidir o que fazer.\n\n" +
            "O que você faz?\n");
        
        scene.addOption(new SceneOption("1", 
            "Organizar evacuação massiva dos alunos para o bunker", 
            "NIUWE_EVACUATION", "niuweSalvouMaisAlunos=true"));
        
        scene.addOption(new SceneOption("2", 
            "Focar em ir direto para a Estação 4 e enfrentar Sepharoth", 
            "NIUWE_DIRECT_CONFRONTATION", "niuweFoiImpulsivo=true"));
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweHeliScene() {
        Scene scene = new Scene("NIUWE_HELI", Player.NIUWE,
            "[NIUWË]\n\n" +
            "Você chega à Estação 4 ofegante. O helicóptero já está sendo " +
            "preparado. Taila Mensa-Viktör está na cabine, checando os " +
            "instrumentos com precisão.\n\n" +
            "— Niuwë! Já era hora! — grita Taila pelo comunicador.\n\n" +
            "Você sobe na aeronave. Dentro, você vê Diretor Dasko, Selenna Albin " +
            "com uma pistola de laser nas mãos, e Jorgenssen fazendo sinais para " +
            "o helicóptero subir.\n\n" +
            "Roger toca em seu ombro.\n" +
            "— Estamos prontos?\n\n" +
            "Você olha embaixo. A Varguën parece um campo fantasma. " +
            "As ruas estão vazias. E no horizonte...\n\n" +
            "Pressione ENTER para continuar...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweSepharothScene() {
        Scene scene = new Scene("NIUWE_SEPHAROTH", Player.NIUWE,
            "[NIUWË]\n\n" +
            "A criatura não era como você lembrava.\n\n" +
            "Sepharoth tinha apenas duas cabeças quando você viu da primeira vez. " +
            "Agora tem VINTE E SETE.\n\n" +
            "Uma massa negra de quase 200 metros de altura, com corpos pseudo-humanos " +
            "contorcendo-se, olhos vermelhos como buracos de sucção de realidade, " +
            "e sombras rastejando ao seu redor como um enxame faminto.\n\n" +
            "— Merda... — solta Dasko, a voz tremendo.\n\n" +
            "Selenna abre fogo com o rifle. Os lasers simplesmente atravessam o corpo " +
            "da criatura sem causar qualquer dano.\n\n" +
            "E então você sente algo estranho na sua mente.\n" +
            "Uma presença. Um sussurro.\n\n" +
            "— Niuwë... — uma voz musical, quase feminina, reverberando de lugar nenhum. " +
            "— Você não pode vencer...\n\n" +
            "É Melaninne.\n\n" +
            "Pressione ENTER para continuar...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweEvacuationScene() {
        Scene scene = new Scene("NIUWE_EVACUATION", Player.NIUWE,
            "[NIUWË]\n\n" +
            "Você desliga o telefone e corre pelos corredores caóticos de Varguën.\n\n" +
            "Pelo caminho, você organiza os alunos. Dirige alguns para o Setor Sul 5, " +
            "coordena com o Conselho Estudantil, usa os alto-falantes para dar instruções " +
            "claras e firmes.\n\n" +
            "A maioria dos alunos segue suas ordens. Sua liderança natural os tranquiliza " +
            "um pouco em meio ao caos.\n\n" +
            "Você consegue encaminhar centenas para o bunker.\n\n" +
            "Mas Sepharoth se aproxima rápido demais.\n\n" +
            "Pressione ENTER para continuar...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createNiuweDirectConfrontationScene() {
        Scene scene = new Scene("NIUWE_DIRECT_CONFRONTATION", Player.NIUWE,
            "[NIUWË]\n\n" +
            "Você já sabe o que fazer. Já fez isso antes.\n" +
            "Impulsivamente, você corre direto para a Estação 4.\n\n" +
            "Alguns alunos gritam chamando por você enquanto passa, " +
            "mas você não tem tempo. Se você não impedir Sepharoth, " +
            "ninguém sobreviverá de qualquer forma.\n\n" +
            "Sua impetuosidade é sua maior fraqueza.\n" +
            "Mas talvez seja exatamente o que Varguën precisa agora.\n\n" +
            "Pressione ENTER para continuar...");
        
        scenes.put(scene.id, scene);
    }
    
    // ========== Ato 2 - Ella ==========
    
    private void createEllaStartScene() {
        Scene scene = new Scene("ELLA_START", Player.ELLA,
            "[ELLA]\n\n" +
            "Anos depois...\n\n" +
            "A Varguën se reconstruiu. Mais forte. Mais vigilante.\n\n" +
            "Você é Ella, uma aluna nova na escola-fortaleza. " +
            "Todos falam sobre seu irmão, Niuwë, que morreu num 'acidente hospitalar' " +
            "anos atrás.\n\n" +
            "Mas aos poucos, você vai descobrir que a história é bem diferente.\n" +
            "Bem mais escura.\n\n" +
            "E Melaninne está sussurrando nos corredores, esperando por você.\n\n" +
            "Pressione ENTER para começar o Ato 2...");
        
        scenes.put(scene.id, scene);
    }
    
    private void createGameOverScene() {
        Scene scene = new Scene("GAME_OVER", Player.NIUWE,
            "================================================\n" +
            "LOG DE MISSÃO ENCERRADO\n" +
            "================================================\n\n" +
            "STATUS: FALHA TOTAL\n\n" +
            "A criatura conhecido como Sepharoth consumiu a Varguën.\n" +
            "Sua morte foi inevitável.\n\n" +
            "Mas o que você deixou para trás?\n" +
            "Quantos você salvou?\n" +
            "Quanto você explorou?\n\n" +
            "Esses dados serão transmitidos para o Ato 2.\n\n" +
            "GERANDO PASSWORD...\n" +
            "================================================\n\n");
        
        scenes.put(scene.id, scene);
    }
    
    // ========== Motor de Jogo ==========
    
    public void showCurrentScene() {
        currentScene = scenes.get(state.sceneId);
        
        if (currentScene == null) {
            window.setText("Erro: Cena não encontrada: " + state.sceneId);
            return;
        }
        
        // Limpar
        window.clearText();
        
        // Mostrar conteúdo
        String displayText = currentScene.text;
        window.setText(displayText);
        
        // Mostrar opções se houver
        if (currentScene.hasOptions()) {
            window.appendText("\n\n");
            for (SceneOption opt : currentScene.options) {
                window.appendText(opt.toString() + "\n");
            }
            window.setWaitingForInput(true);
            isWaitingForChoice = true;
        } else {
            window.appendText("\n\nPressione ENTER para continuar...");
            window.setWaitingForInput(false);
            isWaitingForChoice = false;
        }
    }
    
    public void onAdvance() {
        // Ao avançar sem opções, vai para próxima cena
        // Por enquanto, vamos para GAME_OVER como teste
        state.sceneId = "GAME_OVER";
        showCurrentScene();
    }
    
    public void onChoice(String key) {
        if (!isWaitingForChoice) return;
        
        SceneOption selectedOption = currentScene.getOption(key);
        if (selectedOption == null) {
            window.appendText("\n[Opção inválida]\n");
            return;
        }
        
        // Aplicar consequências
        applyConsequences(currentScene.id, key);
        
        // Avançar para próxima cena
        state.sceneId = selectedOption.nextId;
        showCurrentScene();
    }
    
    private void applyConsequences(String sceneId, String optionKey) {
        // Mapear consequências baseado em cena e opção
        if (sceneId.equals("NIUWE_PRIORITY")) {
            if (optionKey.equals("1")) {
                state.niuweSalvouMaisAlunos = true;
                state.sceneId = "NIUWE_EVACUATION";
            } else if (optionKey.equals("2")) {
                state.niuweFoiImpulsivo = true;
                state.sceneId = "NIUWE_DIRECT_CONFRONTATION";
            }
        }
    }
    
    public boolean isWaitingForChoice() {
        return isWaitingForChoice;
    }
    
    public String getCurrentPassword() {
        return state.generatePassword();
    }
}
