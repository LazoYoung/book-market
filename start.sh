session=github-actions-book-market
tmux attach -t $session || tmux new-session -d -s $session
tmux send-keys -t $session C-c
tmux send-keys -t $session "bash" C-m
tmux send-keys -t $session "cd $PWD" C-m
tmux send-keys -t $session "./gradlew bootRun" C-m