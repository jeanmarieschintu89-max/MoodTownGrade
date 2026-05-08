//
// 🏛 OPEN GUI
//

if (args.length == 0) {

    UrbanismeMainGUI.open(p);

    return true;
}

//
// 🏆 /TOPVILLE
//

if (label.equalsIgnoreCase(
        "topville")) {

    ClassementGUI.open(p);

    return true;
}

//
// 🏗 ALIAS PROJETS
//

if (label.equalsIgnoreCase("tprojet")
        || label.equalsIgnoreCase("tprojets")
        || label.equalsIgnoreCase("projet")
        || label.equalsIgnoreCase("projets")) {

    //
    // 🛡 TOWNY CHECK
    //

    if (!TownyHook.canManage(p)) {

        p.sendMessage("");

        p.sendMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        p.sendMessage(
                "§c🏛 Accès refusé"
        );

        p.sendMessage("");

        p.sendMessage(
                "§7Seuls les maires"
        );

        p.sendMessage(
                "§7et assistants municipaux"
        );

        p.sendMessage(
                "§7peuvent gérer les projets."
        );

        p.sendMessage("");

        p.sendMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        p.sendMessage("");

        return true;
    }

    PendingProjectsGUI.open(p);

    return true;
}

//
// 👥 ALIAS VOTES CITOYENS
//

if (label.equalsIgnoreCase("vprojet")
        || label.equalsIgnoreCase("vprojets")) {

    CitizenTownListGUI.open(p);

    return true;
}