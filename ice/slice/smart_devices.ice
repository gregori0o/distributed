
#ifndef HOME_ICE
#define HOME_ICE

module Home
{
    sequence <string> list;

    interface Device
    {
        idempotent string getState();
    };

    module Audio
    {
    	exception InvalidPlaylistName
	    {
	        string reason = "This playlist name is not stored in memory.";
	    };

	    exception InvalidSongName
	    {
	        string reason = "Playlist do not have this song.";
	    };

	    dictionary<string, list> musicMap;

        interface Music extends Device
        {
            void volumeUp();
            void volumeDown();
            idempotent list getPlaylists();
            idempotent musicMap getMusic();
            idempotent list getSongs(optional(1) string playlistName) throws InvalidPlaylistName;
            idempotent void setPlaylist(string playlistName) throws InvalidPlaylistName;
            idempotent void setSong(string songName) throws InvalidSongName;
            void nextSong();
            void prevSong();
            void startStop();
        };
    };

    module Heating
    {
    	struct Date
    	{
    		int day;
    		int month;
    		int year;
    		int hour;
    		int minutes;
    	};

    	struct InformationAboutChangingTemperature
    	{
    		Date start;
    		Date end;
    		int value;
    	};

    	sequence <InformationAboutChangingTemperature> periodsList;

    	exception InvalidDate
    	{
    		string reason = "This date is not valid";
    	};

    	exception DayFromThePast 
    	{
    		string reason = "This period is from teh past.";
    	};

    	exception InvalidTemperature
    	{
    		string reason = "This is not valid value of temperature.";
    	};

    	interface Furnace extends Device
    	{
    		idempotent void setTemperature(int value) throws InvalidTemperature;
    		void addPeriod(Date start, Date end, int value) throws InvalidTemperature, DayFromThePast, InvalidDate;
    		idempotent periodsList getAllPeriods();
    	};
    };

    module Lighting
    {
    	exception NotValidNumberOfBulbs {};
    	exception NotValidValueOfPercentage {};

    	interface Lamp extends Device
	    {
	    	idempotent void turnOn();
	    	idempotent void turnOff();
	    };

	    interface SimpleLamp extends Lamp {};

	    interface BulbsLamp extends Lamp
	    {
	    	idempotent void turnOnFew(short num) throws NotValidNumberOfBulbs;
	    };

	    interface SmoothLamp extends Lamp
	    {
	    	idempotent void turnOnPercentage(short num) throws NotValidValueOfPercentage;
	    };
    };

    module Service
	{
	    interface ServerInformation
	    {
	    	idempotent list getAllIdentities();
	    };
	};
};


#endif
