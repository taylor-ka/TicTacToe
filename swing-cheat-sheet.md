# Swing Cheat Sheet
There are a TON of Swing components and methods! Below are a sampling that you'll find helpful for writing your Tic-Tac-Toe GUI. But remember that google is your best friend! I'd encourage you to look stuff up :))

## [JFrame](https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html)
```
getContentPane()
setTitle(“title”)
setSize(width, height)
setResizable(boolean)
setDefaultCloseOperation(TYPE)
add(Component)
```

## [Container](https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html)
```
pane.setLayout(Layout)
```

## [GridLayout](https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html)
```
new GridLayout(rows, cols)
```

## [JButton](https://docs.oracle.com/javase/tutorial/uiswing/components/button.html)
```
setText(“title”)
setFont(Font)
setForeground(Color)
putClientProperty(key, value)
addActionListener(ActionListener)
removeActionListener(ActionListener)
```

## [JOptionPane](https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html)
```
JOptionPane.showOptionDialog(...)
```

