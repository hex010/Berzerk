public interface KeyHandlerCommand {
    void executeKeyPressed();
    void executeKeyReleased();
}

class LeftKeyCommand implements KeyHandlerCommand {
    KeyHandler keyHandler;

    public LeftKeyCommand(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public void executeKeyPressed() {
        keyHandler.setLeftPressed(true);
    }

    @Override
    public void executeKeyReleased() {
        keyHandler.setLeftPressed(false);
    }
}

class RightKeyCommand implements KeyHandlerCommand {
    KeyHandler keyHandler;

    public RightKeyCommand(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public void executeKeyPressed() {
        keyHandler.setRightPressed(true);
    }

    @Override
    public void executeKeyReleased() {
        keyHandler.setRightPressed(false);
    }
}

class UpKeyCommand implements KeyHandlerCommand {
    KeyHandler keyHandler;

    public UpKeyCommand(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public void executeKeyPressed() {
        keyHandler.setUpPressed(true);
    }

    @Override
    public void executeKeyReleased() {
        keyHandler.setUpPressed(false);
    }
}

class DownKeyCommand implements KeyHandlerCommand {
    KeyHandler keyHandler;

    public DownKeyCommand(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public void executeKeyPressed() {
        keyHandler.setDownPressed(true);
    }

    @Override
    public void executeKeyReleased() {
        keyHandler.setDownPressed(false);
    }
}

class ShootKeyCommand implements KeyHandlerCommand {
    KeyHandler keyHandler;
    public ShootKeyCommand(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    @Override
    public void executeKeyPressed() {
        keyHandler.setShootPressed(true);
    }

    @Override
    public void executeKeyReleased() {
        keyHandler.setShootPressed(false);
    }
}
