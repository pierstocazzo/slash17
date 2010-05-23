package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

/**
 * Illustrate custom Highlight for all line, not just word highlight. <br />
 * After a <a href="http://stackoverflow.com/questions/353505">question on
 * stackOverflow</a>
 * 
 * @author <a href="http://stackoverflow.com/users/6309/vonc">VonC</a>
 */
@SuppressWarnings("serial")
public class TestHighlight extends JFrame
{
	private final Highlighter.HighlightPainter cyanPainter;
	private final Highlighter.HighlightPainter redPainter;

	private static final String LINES = "one\ntwo\nthree\nfour\nfive\nsix\nseven\neight\n";
	private static final int START_INDEX_FIRST_WORD = 0;
	private static final int END_INDEX_FIRST_WORD = 3;
	private static final int START_INDEX_SECOND_WORD = 8;
	private static final int END_INDEX_SECOND_WORD = 14;
	private static final int START_INDEX_THIRD_WORD = 19;
	private static final int END_INDEX_THIRD_WORD = 24;

	/**
	 * Build a sample panel with highlighted text. <br />
	 * One uses DefaultHighlighter.DefaultPainter, two other lines use
	 * MyHighlighter
	 */
	public TestHighlight()
	{
		final JTextPane textPane = new JTextPane();
		textPane.setHighlighter(new MyHighlighter());

		// The next line is important to avoid selection highlighting from interfering with custom paint
		textPane.setSelectionColor(new Color(1.0f, 1.0f, 1.0f, 0.0f));

		textPane.setText(LINES);
		final JScrollPane scrollPane = new JScrollPane(textPane);
		getContentPane().add(scrollPane);

		// Highlight some text

		this.cyanPainter = new DefaultHighlighter.DefaultHighlightPainter(
				Color.cyan);
		this.redPainter = new DefaultHighlighter.DefaultHighlightPainter(
				Color.red);

		try
		{
			textPane.getHighlighter().addHighlight(START_INDEX_FIRST_WORD,
					END_INDEX_FIRST_WORD, DefaultHighlighter.DefaultPainter);
			textPane.getHighlighter().addHighlight(START_INDEX_SECOND_WORD,
					END_INDEX_SECOND_WORD, this.cyanPainter);
			textPane.getHighlighter().addHighlight(START_INDEX_THIRD_WORD,
					END_INDEX_THIRD_WORD, this.redPainter);
		} catch (final BadLocationException ble)
		{
			System.err.println("Ignored in this example");
		}
	}

	/**
	 * Launches the main panel. <br />
	 * Try to change its width.
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(final String[] args)
	{
		final int anInitialWidth = 300;
		final int anInitialHeight = 200;
		final TestHighlight frame = new TestHighlight();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(anInitialWidth, anInitialHeight);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Custom Highlight for highlighting a whole line, not just a word. <br />
	 * Same thing than DefaultHighlighter, except for the width of the
	 * highlight.
	 * 
	 * @author <a href="http://stackoverflow.com/users/6309/vonc">VonC</a>
	 */
	public class MyHighlighter extends DefaultHighlighter
	{

		private JTextComponent component;

		/**
		 * @see javax.swing.text.DefaultHighlighter#install(javax.swing.text.JTextComponent)
		 */
		@Override
		public final void install(final JTextComponent c)
		{
			super.install(c);
			this.component = c;
		}

		/**
		 * @see javax.swing.text.DefaultHighlighter#deinstall(javax.swing.text.JTextComponent)
		 */
		@Override
		public final void deinstall(final JTextComponent c)
		{
			super.deinstall(c);
			this.component = null;
		}

		/**
		 * Same algo, except width is not modified with the insets.
		 * 
		 * @see javax.swing.text.DefaultHighlighter#paint(java.awt.Graphics)
		 */
		@Override
		public final void paint(final Graphics g)
		{
			final Highlighter.Highlight[] highlights = getHighlights();
			final int len = highlights.length;
			for (int i = 0; i < len; i++)
			{
				Highlighter.Highlight info = highlights[i];
				if (info.getClass().getName().indexOf("LayeredHighlightInfo") > -1)
				{
					// Avoid allocing unless we need it.
					final Rectangle a = this.component.getBounds();
					final Insets insets = this.component.getInsets();
					a.x = insets.left;
					a.y = insets.top;
					// a.width -= insets.left + insets.right + 100;
					a.height -= insets.top + insets.bottom;
					for (; i < len; i++)
					{
						info = highlights[i];
						if (info.getClass().getName().indexOf(
								"LayeredHighlightInfo") > -1)
						{
							final Highlighter.HighlightPainter p = info
									.getPainter();
							p.paint(g, info.getStartOffset(), info
									.getEndOffset(), a, this.component);
						}
					}
				}
			}
		}
	}
}