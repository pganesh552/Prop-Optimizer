import { createContext, useState } from "react";

export const PlayerContext = createContext();

export const PlayerProvider = ({ children }) => {
  const [selectedPlayer, setSelectedPlayer] = useState(null);

  return (
    <PlayerContext.Provider value={{ selectedPlayer, setSelectedPlayer }}>
      {children}
    </PlayerContext.Provider>
  );
};
