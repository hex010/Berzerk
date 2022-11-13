public interface GamePanelCommand {
    void execute();
}

class PlayAgainGameCommand implements  GamePanelCommand {
    GamePanel gamePanel;

    public PlayAgainGameCommand(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void execute() {
        gamePanel.setDefaultGameValues();
    }
}

class QuitGameCommand implements  GamePanelCommand {
    @Override
    public void execute() {
        System.exit(0);
    }
}