package com.development.knowledgehut.urbies.Screens;


import android.content.Context;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import com.development.knowledgehut.urbies.R;

class LoadingScreen extends Screen {

    LoadingScreen(Game game){
        super(game);
        queueAssets();
    }

    @Override
    public void update(float deltaTime) {
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float deltaTime) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onStandardTouch(MotionEvent event) {

    }

    @Override
    public void doFlingEvent(MotionEvent e1, MotionEvent e2) {

    }

    @Override
    public void input(MotionEvent event) {

    }

    private void queueAssets(){
        Graphics graphics = game.getGraphics();
        Context context = game.getRenderView().getContext();

        Assets.level = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.level_screen),
                Graphics.BitmapFormat.ARGB4444);

        Assets.background2  = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.backgnd1),
                Graphics.BitmapFormat.ARGB8888);

        Assets.background  = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.night_bkgnd),
                Graphics.BitmapFormat.ARGB8888);

        Assets.ice_bkgnd  = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.ice_bkgnd),
                Graphics.BitmapFormat.ARGB8888);

        Assets.baby = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.baby_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.nerd = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.nerd_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.pigtails = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pigtails_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.pac = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pac_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.punk = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.punk_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.lady = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.lady_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.nerd_girl = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.nerd_girl_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.rocker = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.rocker_anim),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.chameleon = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cham),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.stripe_v = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.vertical),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.stripe_h = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.horizontal),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.magician = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.magic2),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.magicBomb = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.bomb),
                2, Graphics.BitmapFormat.ARGB4444);

        Assets.glassTileAnim = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.glass_anim),
                6, Graphics.BitmapFormat.ARGB4444);

        Assets.glassTile = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.glass_tile),
                Graphics.BitmapFormat.ARGB4444);

        Assets.failed = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.failed),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_baby = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_baby),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_lady = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_lady),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_nerd = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_nerd),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_nerdgirl = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_nerdgirl),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_pac = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_pac),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_pigtails = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_pigtails),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_punk = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_punk),
                Graphics.BitmapFormat.ARGB4444);

        Assets.small_rocker = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.small_rocker),
                Graphics.BitmapFormat.ARGB4444);

        Assets.selector = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.selector),
                Graphics.BitmapFormat.ARGB4444);

        Assets.bright_selector = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pink_selector),
                Graphics.BitmapFormat.ARGB4444);

        Assets.play = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.play_xs),
                Graphics.BitmapFormat.ARGB4444);

        Assets.menu = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.menu),
                Graphics.BitmapFormat.ARGB4444);

        Assets.playOn = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.play_on),
                Graphics.BitmapFormat.ARGB4444);

        Assets.tutorial = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.tutorial),
                Graphics.BitmapFormat.ARGB4444);

        Assets.pause = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pause),
                Graphics.BitmapFormat.ARGB4444);

        Assets.board = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.board3),
                Graphics.BitmapFormat.ARGB4444);

        Assets.help = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.help),
                Graphics.BitmapFormat.ARGB4444);

        Assets.speechBubble = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.speechbubble1),
                Graphics.BitmapFormat.ARGB4444);

        Assets.speechTitle = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.speechbubble),
                Graphics.BitmapFormat.ARGB4444);

        Assets.speechCelebrate = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.celebrate_speech),
                Graphics.BitmapFormat.ARGB4444);

        Assets.victory = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.victory),
                Graphics.BitmapFormat.ARGB4444);

        Assets.tile = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.tile_42x42),
                Graphics.BitmapFormat.ARGB4444);

        Assets.holder = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.level),
                Graphics.BitmapFormat.ARGB4444);

        Assets.button = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pink_button),
                Graphics.BitmapFormat.ARGB4444);

        Assets.padlock = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.button_locked),
                Graphics.BitmapFormat.ARGB4444);

        Assets.tileShade = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.tile_shade),
                Graphics.BitmapFormat.ARGB4444);

        Assets.title = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.urbies),
                Graphics.BitmapFormat.RGB565);

        Assets.babyBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.baby_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.nerdBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.nerd_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.pacBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pac_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.pigtailsBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.pigtails_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.ladyBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.lady_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.punkBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.punk_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.rockerBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.rocker_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.nerdGirlBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.nerd_girl_bounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.chameleonBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.chambounce),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.stripeBounce_h = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.hori_bounce),
                16, Graphics.BitmapFormat.ARGB4444);

        Assets.stripeBounce_v = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.vertical_bounce),
                16, Graphics.BitmapFormat.ARGB4444);

        Assets.magicianBounce = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.magic_bounce2),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.white_chocolate_fade = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cham_fade),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.horizontal_fade = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.horizontal_fade),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.vertical_fade = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.vertical_fade),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.magician_fade_in_out = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.magic_fade2),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.wizard_attack = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.wizard_attack),
                3, Graphics.BitmapFormat.ARGB4444);

        Assets.wizard_idle = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.wizard_idle),
                4, Graphics.BitmapFormat.ARGB4444);

        Assets.colourBombExplosion = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.colourbomb),
                5, Graphics.BitmapFormat.ARGB4444);

        Assets.lightning0 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.lightning1r),
                Graphics.BitmapFormat.ARGB4444);

        Assets.lightning1 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.lightning2r),
                Graphics.BitmapFormat.ARGB4444);

        Assets.electric1 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.electric1_vertical),
                Graphics.BitmapFormat.ARGB4444);

        Assets.electric2 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.electric2_vertical),
                Graphics.BitmapFormat.ARGB4444);

        Assets.electric3 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.electric3_vertical),
                Graphics.BitmapFormat.ARGB4444);

        Assets.muzzle = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.muzzle_flash),
                4, Graphics.BitmapFormat.ARGB4444);

        Assets.wood_100 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.wood_100),
                Graphics.BitmapFormat.ARGB4444);

        Assets.wood_25 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.wood_25),
                Graphics.BitmapFormat.ARGB4444);

        Assets.wood_break_anim = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.wood_break_anim),
                7, Graphics.BitmapFormat.ARGB4444);

        Assets.cement_100 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cement_100),
                Graphics.BitmapFormat.ARGB4444);

        Assets.cement_75 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cement_75),
                Graphics.BitmapFormat.ARGB4444);

        Assets.cement_50 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cement_50),
                Graphics.BitmapFormat.ARGB4444);

        Assets.cement_25 = graphics.scaledBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cement_25),
                Graphics.BitmapFormat.ARGB4444);

        Assets.cement_break_anim = graphics.animatedBitmap(context,
                AppCompatDrawableManager.get().getDrawable(context, R.drawable.cement_break_anim),
                6, Graphics.BitmapFormat.ARGB4444);

        Assets.babyFreed = game.getAudio().newSound("baby.mp3");

        Assets.pacFreed = game.getAudio().newSound("pac.mp3");

        Assets.nerdFreed = game.getAudio().newSound("nerd.mp3");

        Assets.pigtailsFreed = game.getAudio().newSound("freed2.mp3");

        Assets.ladyFreed = game.getAudio().newSound("lady.mp3");

        Assets.girlNerdFreed = game.getAudio().newSound("girl_nerd.mp3");

        Assets.rockerFreed = game.getAudio().newSound("rocker.mp3");

        Assets.punkFreed = game.getAudio().newSound("punk.mp3");

        Assets.typeface1 = graphics.setTypeface(context, "Fonts/sf_slapstick_comic.ttf");

    }
}
