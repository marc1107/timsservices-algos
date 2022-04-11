package aufgabe4;

public class TelVerbindung
{
	int c;
	TelKnoten anfang;
	TelKnoten ende;

	public TelVerbindung(TelKnoten anfang, TelKnoten ende, int c)
	{
		this.c = c;
		this.anfang = anfang;
		this.ende = ende;
	}

	@Override
	public String toString()
	{
		return String.format("[%s -> %s] %d", this.anfang, this.ende, this.c);
	}
}
