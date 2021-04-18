package com.fallingangel.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.view.AchievementsView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.HelpView1;
import com.fallingangel.view.HelpView2;
import com.fallingangel.view.HelpView3;
import com.fallingangel.view.HighScoreListView;
import com.fallingangel.view.MenuView;
import com.fallingangel.view.MultiPlayerView;
import com.fallingangel.view.SettingsView;

public class MainController extends ClickListener {

    public FallingAngel game;
    //initializing the different views
    public GameView gameView = new GameView();
    public GameOverView gameOverView = new GameOverView();
    public HelpView1 helpView1 = new HelpView1();
    public HelpView2 helpView2 = new HelpView2();
    public HelpView3 helpView3 = new HelpView3();
    public AchievementsView achievementsView = new AchievementsView();
    public HighScoreListView highscorelistView = new HighScoreListView();
    public SettingsView settingsView = new SettingsView();
    public MenuView menuView;
    public MultiPlayerView multiPlayerView = new MultiPlayerView();
    public Asset asset;
    private Sound clickSound;
    //private Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_sound.wav"));


    public MainController() {
        this.game = FallingAngel.getInstance();
        clickSound = asset.clickSound;
    }

    public void setStartScreen(){ //setStartScreen method is called in the game class.
        this.menuView = new MenuView();
        game.setScreen(menuView);
    }

    /* fjerne disse?

    public void changeView(Screen nextScreen){
        game.setScreen(nextScreen);
    }

  public MenuView getMenuView(){
      return menuView;
    }

    public GameView getGameView(){
        return gameView;
    }

     */

    @Override
    public boolean handle(Event event) { //the Main controller listenens to the buttons on the different views and changes bewteen the different views
        if (event.getListenerActor().equals(menuView.getSinglePlayerButton())) {
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            //long id = clickSound.play(0.2f);
            //clickSound.setPitch(id,2);
            //clickSound.setLooping(id,false);
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(multiPlayerView);
            multiPlayerView.connectToGameRoom();
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getQuestionButton())){ //fjerne denne
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(helpView1);
            return true;
        }

        else if (event.getListenerActor().equals(menuView.getAchievementsButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(achievementsView);
            return true;
        }


        else if (event.getListenerActor().equals(menuView.getHighscoreListButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(highscorelistView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getSettingsButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(settingsView);
            return true;
        }
        else if (event.getListenerActor().equals(achievementsView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(highscorelistView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(menuView);
            return true;
        }
        /*else if (event.getListenerActor().equals(gameView.getGameOverButton())){
            clickSound.play(0.2f);
            game.setScreen(gameOverView);
            return true;
        }*/
        else if (event.getListenerActor().equals(helpView1.getNextButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(helpView2);
            return true;
        }
        else if (event.getListenerActor().equals(helpView2.getNextButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(helpView3);
            return true;
        }
        else if (event.getListenerActor().equals(helpView3.getNextButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(menuView);
            return true;
        }

        else if (event.getListenerActor().equals(settingsView.getMusicOnButton())){
            clickSound.play(0.0f);
            game.music.pause();
            //settingsView.stage.clear();
            //settingsView.stage.addActor(settingsView.getMusicOffButton());
            //game.setScreen(settingsView);
            //settingsView.getMusicOnButton().remove();
            //settingsView.getMusicOnButton().setVisible(false);
            //settingsView.getMusicOffButton().setVisible(true);
            settingsView.getMusicOnButton().setPosition(-1000,-1000);
            settingsView.getMusicOffButton().setPosition(Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
            //settingsView.stage.addActor(settingsView.rightSoundButton());
            //settingsView.getMusicOnButton().remove();
            //settingsView.stage.draw();
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getMusicOffButton())){
            clickSound.play(0.2f);
            game.music.setVolume(0.02f);
            game.music.play();
            settingsView.getMusicOffButton().setPosition(-1000,-1000);
            settingsView.getMusicOnButton().setPosition(Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
            //settingsView.stage.clear();
            //settingsView.stage.addActor(settingsView.getMusicOnButton());
            //game.setScreen(settingsView);
            //settingsView.getMusicOffButton().remove();
            //settingsView.stage.draw();
            return true;
        }
        else{
            return false;
        }
    }
}