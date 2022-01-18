package com.tfw.scoreboard;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     In this class we gonna have async task finishes
 *     and updates all necessary requirements in scoreboard and tab-list
 *
 */

                    fastBoard.getScoreboard().getTeam(teamName).addEntry(playerData.getPlayerName());
                }


                if (!playerData.getClanTag().equalsIgnoreCase(""))
                    fastBoard.getScoreboard().getTeam(teamName).setSuffix(" §7▏ §b" + playerData.getClanTag());
                else fastBoard.getScoreboard().getTeam(teamName).setSuffix("");

                fastBoard.getScoreboard().getTeam(teamName).setPrefix(playerData.getRankColor().toString());
            }
        });
    */}


    public static void ping_clanChange(PlayerBoard fastBoard) {
       /* for (final PlayerData playerData : new ArrayList<>(PlayerManager.getPlayerDataList())) {
            if (playerData.isOnline()) {
                String teamName = playerData.getTeam();

                if (fastBoard.getScoreboard().getTeam(teamName) == null)
                    fastBoard.getScoreboard().registerNewTeam(teamName);

                if (!fastBoard.getScoreboard().getTeam(teamName).hasEntry(playerData.getPlayerName())) {
                    //suffiex = suffiex.length() >= 16 ? suffiex.substring(0, 14) : suffiex;
                    fastBoard.getScoreboard().getTeam(teamName).addEntry(playerData.getPlayerName());
                }
                fastBoard.getScoreboard().getTeam(teamName).setSuffix("");
                if (playerData.getClanTag().equalsIgnoreCase(""))
                    fastBoard.getScoreboard().getTeam(teamName).setSuffix("");

                fastBoard.getScoreboard().getTeam(teamName).setPrefix(playerData.getRankColor() + "");
            }
        }*/
    }

    public static void updateTitle(){
        for (PlayerBoard playerBoard : getBoardArrayList())
            if (playerBoard.getIScoreboard() != null && !playerBoard.getIScoreboard().isAnimated())
                playerBoard.updateTitle();
    }


    @Override
    public void run() {

        for (PlayerBoard scoreBoard : getBoardArrayList()){
            if (scoreBoard == null)
                continue;
            else if(!scoreBoard.getPlayerData().getSettings().isRefresh())
                continue;

            generate_ifExists(scoreBoard);

            //Update lines
            updateBoard(scoreBoard);
        }
    }

    private void updateBoard(PlayerBoard scoreBoard){
        List<String> replaceHolders = Style.translateLines_Holders(scoreBoard.getPlayerData().getPlayer(), scoreBoard.getIScoreboard().lines());

        scoreBoard.updateTitle();
        scoreBoard.updateLines(replaceHolders);
    }

}
